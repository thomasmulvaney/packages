(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.10.3" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "1.2.4")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom  {:project     'cljsjs/sax
       :version     +version+
       :description "A sax style parser for JS"
       :url         "https://github.com/isaacs/sax-js"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
   (download :url (format "https://unpkg.com/sax@%s/lib/sax.js" +lib-version+))
   (sift :move {#"^sax.js"     "cljsjs/sax/development/sax.inc.js"})
   (minify :in "cljsjs/sax/development/sax.inc.js"
           :out "cljsjs/sax/production/sax.min.inc.js")
   (deps-cljs :provides ["sax" "cljsjs.sax"]
              :global-exports '{sax sax})
   (pom)
   (jar)
   (validate)))

