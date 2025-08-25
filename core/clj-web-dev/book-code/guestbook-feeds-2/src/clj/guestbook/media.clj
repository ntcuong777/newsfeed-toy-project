;---
; Excerpted from "Web Development with Clojure, Third Edition",
; published by The Pragmatic Bookshelf.
; Copyrights apply to this code. It may not be used to create training material,
; courses, books, articles, and the like. Contact us if you are in doubt.
; We make no guarantees that this code is fit for any purpose.
; Visit http://www.pragmaticprogrammer.com/titles/dswdcloj3 for more book information.
;---
(ns guestbook.media
  (:require
   [guestbook.db.core :as db]
   [clojure.tools.logging :as log])
  (:import [java.awt.image AffineTransformOp BufferedImage]
           [java.io ByteArrayOutputStream]
           java.awt.geom.AffineTransform
           javax.imageio.ImageIO))

(defn insert-image-returning-name [{:keys [tempfile filename]}
                                   {:keys [width height
                                           max-width max-height
                                           owner]}]
  (let [baos (ByteArrayOutputStream.)
        img (ImageIO/read tempfile)
        img-width (.getWidth img)
        img-height (.getHeight img)
        ratio (min (/ (or width img-width) img-width)
                   (/ (or max-width img-width) img-width)
                   (/ (or height img-height) img-height)
                   (/ (or max-height img-height) img-height))
        img-scaled (if (or (= 1 ratio) (and (> ratio 1)
                                            (nil? height)
                                            (nil? width)))
                     img
                     (let [scale
                           (AffineTransform/getScaleInstance
                            (double ratio) (double ratio))

                           transform-op
                           (AffineTransformOp.
                            scale  AffineTransformOp/TYPE_BILINEAR)]
                       (.filter transform-op
                                img
                                (BufferedImage. (* ratio img-width)
                                                (* ratio img-height)
                                                (.getType img)))))]
    (ImageIO/write img-scaled "png" baos)
    (if (= 0
           (db/save-file! {:name filename
                           :data (.toByteArray baos)
                           :owner owner
                           :type "image/png"}))
      (do
        (log/error "Attempted to overwrite an image that you don't own!")
        (throw (ex-info "Attempted to overwrite an image that you don't own!"
                        {:name filename})))
      filename)))
