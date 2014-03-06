(load (current-pathname "micro-graphics/load.lisp"))
(load (current-pathname "micro-graphics/load2.lisp"))
(load (current-pathname "micro-graphics/clmg-examples.lisp"))


;; class movable-object ;;
(defclass movable-object (picture)
  ((ready-to-movep :initform nil)))

;; get/set
(defmethod ready-to-movep ((mo movable-object))
  (slot-value mo 'ready-to-movep))

(defmethod set-ready-to-movep ((mo movable-object) value)
  (if (or (eq value t) (eq value nil))
  (setf (slot-value mo 'ready-to-movep) value)))


(defmethod ev-mouse-down ((mo movable-object) sender origin button position)
  (call-next-method)
  (print (ready-to-movep mo))
  (if (ready-to-movep mo)
      (set-ready-to-movep mo nil)
    (set-ready-to-movep mo t))
  (send-event mo 'ev-item-to-copy))


;; class board ;;
(defclass board (picture)
  ((item-to-copy :initform nil)
   (last-click :initform nil)))

;; get/set
(defmethod item-to-copy ((b board))
  (slot-value b 'item-to-copy))

(defmethod set-item-to-copy ((b board) value)
  (setf (slot-value b 'item-to-copy) value))

(defmethod last-click ((b board))
  (slot-value b 'last-click))

(defmethod set-last-click ((b board) value)
  (setf (slot-value b 'last-click) value))


(defmethod initialize-instance ((b board) &key)
  (call-next-method)
  (let ((bg (make-instance 'full-shape)))
    (set-color bg :lightblue)
    (set-items b (list bg))
    (set-item-events b))
  b)


(defmethod set-item-events ((b board))
  (dolist (p (items b))
    (if (not (typep p 'full-shape))
        (add-event p 'ev-item-to-copy))))

(defmethod add-picture ((b board) new-item)
  (if (typep new-item 'movable-object)
      (progn (set-items b (append (list new-item) (items b)))
        (set-item-events b))))

(defmethod ev-item-to-copy ((b board) shape)
  (set-item-to-copy b t)
  (let ((copy-item shape)) 
    (print "Copy successful")
    (set-item-to-copy b copy-item)))

(defmethod ev-mouse-down ((b board) origin sender mouse pt)
  (call-next-method)
  (if (eq nil (item-to-copy b))
      (set-last-click b pt)
    (let ((c (item-to-copy b))
          (l-click (last-click b)))
        (set-last-click b nil)
        (let ((lx (x l-click))
              (ly (y l-click))
              (px (x pt))
              (py (y pt)))
          (move c (- px lx) (- py ly))

          (set-items b (append (list c) (items b)))
          (set-item-events b)
          (set-item-to-copy b nil)))))



(defun make-random-circle ()
  (let ((c (make-instance 'circle)))
    (set-radius c (+ (random 10) 10))
    (set-color c :blue)
    (set-filledp c t)
    (move c (+ (random 200) 100) (+ (random 200) 100))
    c))

(defun make-triangle ()
  (let ((tri (make-instance 'polygon)))
    (set-items tri (list (make-point 0 30) (make-point 30 30) (make-point 15 0)))
    (set-closedp tri t)
    (set-filledp tri t)
    (move tri (+ (random 200) 100) (+ (random 200) 100))
    tri))


(defun start-app ()
  (let ((w (make-instance 'window))
        (work (make-instance 'board))
        (p1 (make-instance 'movable-object))
        (p2 (make-instance 'movable-object))
        (p3 (make-instance 'movable-object)))
    
    (set-items p1 (list (make-random-circle)))
    (set-items p2 (list (make-random-circle)))
    (set-items p3 (list (make-triangle)))
        
    (add-picture work p1)
    (add-picture work p2)
    (add-picture work p3)

    
    (set-shape w work)
    (redraw w)))

(start-app)
        
        