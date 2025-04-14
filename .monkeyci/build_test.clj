(ns build-test
  (:require [build :as sut]
            [clojure.string :as cs]
            [clojure.test :refer [deftest testing is]]
            [monkey.ci.build.v2 :as m]
            [monkey.ci.test :as mt]))

(deftest jobs
  (testing "contains build and image jobs"
    (is (= 2 (count sut/jobs)))))

(deftest build-site
  (let [job sut/build-site]
    (testing "is container job"
      (is (m/container-job? job)))

    (testing "runs clojure"
      (is (cs/starts-with? (-> job :script first) "clojure")))))

(deftest image
  (mt/with-build-params {}
    (let [job (sut/image mt/test-ctx)]
      (testing "is container job"
        (is (m/container-job? job)))

      (testing "depends on build-site"
        (is (= ["build-site"] (:dependencies job)))))))

(deftest get-version
  (testing "`latest` if main branch"
    (is (= "latest" (-> mt/test-ctx
                        (mt/with-git-ref "refs/heads/main")
                        (sut/get-version)))))

  (testing "tag if tag given"
    (is (= "1.2.3" (-> mt/test-ctx
                       (mt/with-git-ref "refs/tags/1.2.3")
                       (sut/get-version))))))
