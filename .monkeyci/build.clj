(ns journal.build
  (:require [babashka.fs :as fs]
            [monkey.ci.build
             [api :as api]
             [core :as c]
             [shell :as s]]))

(def build-site
  {:name "Build website files"
   :container/image "docker.io/clojure:temurin-21-tools-deps-bookworm-slim"
   :script ["clojure -M:build"]})

(def private-key (fs/expand-home "~/privkey"))

(def get-privkey
  {:name "Download private key"
   :action (fn [ctx]
             (s/param-to-file ctx "host-private-key" private-key))})

(def privkey-permissions
  {:name "Set private key permissions"
   :action (s/bash (str "chmod 600 " private-key))})

(def ssh-dir "/root/.ssh")
(def privkey-remote (str ssh-dir "/id_rsa"))
(def host "10.24.1.21")

(defn deploy
  "Ssh into the remote host, and rsync the generated journal files"
  [ctx]
  (let [fingerprint (-> ctx
                        (api/build-params)
                        (get "host-fingerprint"))]
    {:name "Deploy site"
     :container/image "docker.io/alpine:latest"
     :container/mounts [[private-key privkey-remote]]
     :script ["apk update"
              "apk add rsync openssh-client-default"
              (format "echo '%s ssh-ed25519 %s' > %s/known_hosts" host fingerprint ssh-dir)
              (format "rsync -mir public/blog/ monkeyci@%s:/var/www/html/monkeyci/blog" host)]}))

(c/defpipeline build-and-deploy
  [build-site
   get-privkey
   privkey-permissions
   deploy])

[build-and-deploy]
