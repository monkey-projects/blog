(ns build
  (:require [monkey.ci.build.v2 :as m]
            [monkey.ci.plugin.kaniko :as pk]))

(def site-artifact
  (m/artifact "site" "public/blog"))

(def build-site
  (-> (m/container-job "build-site")
      (m/image "docker.io/clojure:temurin-23-tools-deps-bookworm-slim")
      (m/script ["clojure -M:build"])
      (m/save-artifacts [site-artifact])))

(defn get-version [ctx]
  (or (m/tag ctx) "latest"))

(defn image [ctx]
  ((pk/image-job {:target-img (str "fra.ocir.io/frjdhmocn5qi/monkeyprojects/blog:" (get-version ctx))
                  :arch :amd
                  :job-id "image"
                  :container-opts {:dependencies ["build-site"]
                                   :restore-artifacts [site-artifact]}})
   ctx))

(def jobs
  [build-site
   image])
