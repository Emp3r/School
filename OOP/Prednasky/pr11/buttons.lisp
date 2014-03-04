;; klikani na polygony a jejich rozliseni

(setf tl1 (make-instance 'polygon))
(set-items tl1 (list (set-y (set-x (make-instance 'point) 10) 50)
                     (set-y (set-x (make-instance 'point) 50) 50)
                     (set-y (set-x (make-instance 'point) 50) 10)
                     (set-y (set-x (make-instance 'point) 10) 10)))
(set-filledp tl1 t)

#|
(setf win (make-instance 'window))
(set-shape win tl1)
(add-event tl1 'ev-mouse-down)
|#

(setf tl2 (make-instance 'polygon))
(set-items tl2 (list (set-y (set-x (make-instance 'point) 110) 50)
                     (set-y (set-x (make-instance 'point) 150) 50)
                     (set-y (set-x (make-instance 'point) 150) 10)
                     (set-y (set-x (make-instance 'point) 110) 10)))
(set-filledp tl2 t)
(add-event tl2 'ev-mouse-down)

(setf pic (make-instance 'picture))
(set-items pic (list tl1 tl2))

#|
(setf win (make-instance 'window))
(set-shape win pic)
(add-event pic 'ev-mouse-down)
|#

;; funkcni varianta

(defclass my-window (window)
  ())

(defmethod ev-mouse-down ((win my-window) sender origin but pos)
  (if (eql tl1 origin) (format t "Prvni ~%")
    (format t "Druhy ~%")))

#|
(setf win (make-instance 'my-window))
(set-shape win pic)
(add-event pic 'ev-mouse-down)
|#



;; hezci tlacitka

(defclass button (picture)
  ())

(defun coords-to-points (coords)
  (when coords
    (let ((coord (car coords)))
      (cons (move (make-instance 'point)
                  (first coord)
                  (second coord))
            (coords-to-points (cdr coords))))))

(defun make-button (desc left top right bot thick)
  (let ((but (make-instance 'button))
        (frame (make-instance 'polygon))
        (backg (make-instance 'polygon))
        (text (make-instance 'text-shape))
        (coords `((,left ,top) (,left ,bot) (,right ,bot) (,right ,top))))
    (set-items frame (coords-to-points coords))
    (set-thickness frame thick)
    (set-items backg (coords-to-points coords))
    (set-color backg :white)
    (set-filledp backg t)
    (set-text text desc)
    (move text (+ 5 left) (+ 5 top (/ (- bot top) 2)))
    (set-items but (list text frame backg))))

(defmethod mouse-down ((but button) b pos)
  (send-event but 'ev-mouse-down but b pos))

#|
(setf win (make-instance 'my-window))
(setf but (make-button "Prvni" 0 0 50 20 4))
(set-shape win but)
((move but 50 20)
(scale but 2 (make-instance 'point))
(add-event but 'ev-mouse-down)
|#

(defclass my-window2 (window)
  ())

(defmethod ev-mouse-down ((win my-window2) sender origin b pos)
  (if (eql but origin) (format t "Prvni ~%")
    (format t "Druhy ~%")))

#|
(setf win (make-instance 'my-window2))
(setf pic (make-instance 'picture))
(setf but (make-button "Prvni" 10 10 60 30 4))
(add-event but 'ev-mouse-down)
(setf but2 (make-button "Druhy" 80 10 130 30 4))
(add-event but2 'ev-mouse-down)
(set-items pic (list but but2))
(set-shape win pic)
(add-event pic 'ev-mouse-down)
|#

#|
(defmethod ev-mouse-down ((win my-window2) sender origin b pos)
  (if (eql (first (items (shape win))) origin) (format t "Prvni ~%")
    (format t "Druhy ~%")))
|#



;; prejmenovani udalosti

(defclass my-window3 (window)
  ())

(defmethod ev-first ((win my-window3) sender origin b p)
  (format t "Prvni ~%"))
  
(defmethod ev-second ((win my-window3) sender origin b p)
  (format t "Druhy ~%"))

#|
(setf win (make-instance 'my-window3))
(setf pic (make-instance 'picture))
(setf but (make-button "Prvni" 10 10 60 30 4))
(setf but2 (make-button "Druhy" 80 10 130 30 4))
(set-items pic (list but but2))
(add-event but '(ev-mouse-down ev-first))
(add-event but2 '(ev-mouse-down ev-second))
(set-shape win pic)
(add-event pic 'ev-first)
(add-event pic 'ev-second)
|#

; aby fungovalo

(defclass my-picture (picture)
  ())

(defmethod ev-first ((pic my-picture) sender origin b p)
  (send-event pic 'ev-first origin b p))
  
(defmethod ev-second ((win my-picture) sender origin b p)
  (send-event pic 'ev-second origin b p))

#|
(setf win (make-instance 'my-window3))
(setf pic (make-instance 'my-picture))
(setf but (make-button "Prvni" 10 10 60 30 4))
(setf but2 (make-button "Druhy" 80 10 130 30 4))
(set-items pic (list but but2))
(add-event but '(ev-mouse-down ev-first))
(add-event but2 '(ev-mouse-down ev-second))
(set-shape win pic)
(add-event pic 'ev-first)
(add-event pic 'ev-second)
|#




; s novou verzi

(defclass my-window4 (window)
  ())

(defmethod initialize-instance ((win my-window4) &key)
  (call-next-method)
  (add-handler win 'ev-first (function ev-first))
  (add-handler win 'ev-second (function ev-second))
  win)

(defmethod ev-first ((win my-window4) sender origin b p)
  (format t "Prvni ~%"))
  
(defmethod ev-second ((win my-window4) sender origin b p)
  (format t "Druhy ~%"))


#|
(setf win (make-instance 'my-window4))
(setf pic (make-instance 'picture))
(setf but (make-button "Prvni" 10 10 60 30 4))
(setf but2 (make-button "Druhy" 80 10 130 30 4))
(set-items pic (list but but2))
(add-event but '(ev-mouse-down ev-first))
(add-event but2 '(ev-mouse-down ev-second))
(set-shape win pic)
(add-event pic 'ev-first)
(add-event pic 'ev-second)
(add-handler pic 'ev-first (lambda (&rest args) (print "ted")))
|#


