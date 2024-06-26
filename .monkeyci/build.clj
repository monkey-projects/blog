(ns journal.build
  (:require [babashka.fs :as fs]
            [monkey.ci.build
             [api :as api]
             [core :as c]
             [shell :as s]]))

(def site-artifact
  {:id "site"
   :path "public/blog"})

(def build-site
  (c/container-job
   "build-site"
   {:container/image "docker.io/clojure:temurin-21-tools-deps-bookworm-slim"
    :script ["clojure -M:build"]
    :save-artifacts [site-artifact]}))

(def ssh-dir "/root/.ssh")
(def privkey-remote (str ssh-dir "/id_rsa"))
(def host "10.24.1.21")

(defn deploy
  "Ssh into the remote host, and rsync the generated journal files"
  [ctx]
  (let [params (api/build-params ctx)
        fingerprint (get params "host-fingerprint")
        privkey (get params "host-private-key")
        pk-var "PRIVATE_KEY"]
    (c/container-job
     "deploy-site"
     {:container/image "docker.io/alpine:latest"
      :container/env {pk-var privkey}
      :script ["apk update"
               "apk add rsync openssh-client-default"
               (format "mkdir -p %s" ssh-dir)
               ;; Use printf to preserve newlines
               (format "printf -- \"$%s\" > %s" pk-var privkey-remote)
               (format "chown 600 %s" privkey-remote)
               (format "echo '%s ssh-ed25519 %s' > %s/known_hosts" host fingerprint ssh-dir)
               (format "rsync -mir public/blog/ monkeyci@%s:/var/www/html/monkeyci/blog" host)]
      :restore-artifacts [site-artifact]
      :dependencies ["build-site"]})))

[build-site
 deploy]
