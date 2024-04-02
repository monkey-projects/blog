(ns journal.build
  (:require [babashka.fs :as fs]
            [monkey.ci.build
             [api :as api]
             [core :as c]
             [shell :as s]]))

(def build-site
  (c/container-job
   "build-site"
   {:container/image "docker.io/clojure:temurin-21-tools-deps-bookworm-slim"
    :script ["clojure -M:build"]}))

(def private-key "privkey")
(def private-key-artifact
  {:id "privkey"
   :path private-key})

(def get-privkey
  (c/action-job
   "priv-key"
   (fn [ctx]
     (s/param-to-file ctx "host-private-key" private-key))
   {:save-artifacts [private-key-artifact]}))

(def ssh-dir "/root/.ssh")
(def privkey-remote (str ssh-dir "/id_rsa"))
(def host "10.24.1.21")

(defn deploy
  "Ssh into the remote host, and rsync the generated journal files"
  [ctx]
  (let [fingerprint (-> ctx
                        (api/build-params)
                        (get "host-fingerprint"))]
    (c/container-job
     "deploy-site"
     {:container/image "docker.io/alpine:latest"
      :script ["apk update"
               "apk add rsync openssh-client-default"
               (format "mv %s %s" private-key privkey-remote)
               (format "chown %s 600" privkey-remote)
               (format "echo '%s ssh-ed25519 %s' > %s/known_hosts" host fingerprint ssh-dir)
               (format "rsync -mir public/blog/ monkeyci@%s:/var/www/html/monkeyci/blog" host)]
      :restore-artifacts [private-key-artifact]
      :dependencies ["priv-key" "build-site"]})))

[build-site
 get-privkey
 deploy]
