(load "./micro-graphics/load.lisp")

(defvar *mgw*)
(setf *mgw* (mg:display-window))

(mg:set-callback *mgw* :activate 
              (lambda (mgw &rest args)
                (declare (ignore mgw))
                (print "activate")
                (print args)))

(mg:set-callback *mgw* :character
              (lambda (mgw &rest args)
                (declare (ignore mgw))
                (print "character")
                (print args)))


(mg:set-callback *mgw* :destroy
              (lambda (mgw &rest args)
                (declare (ignore mgw))
                (print "destroy")
                (print args)))

(mg:set-callback *mgw* :mouse-down
              (lambda (mgw &rest args)
                (declare (ignore mgw))
                (print "mouse-down")
                (print args)))


(mg:set-callback *mgw* :display
              (lambda (mgw &rest args)
                (declare (ignore mgw))
                (print "display")
                (print args)))

(mg:get-callback *mgw* :display)



;; class WINDOW - changes

(defmethod install-callbacks ((w window))
  (mg:set-callback (slot-value w 'mg-window)
		   :display (lambda (mgw)
                              (declare (ignore mgw))
                              (redraw w)))
  w)


(defmethod initialize-instance ((w window) &key)
  (call-next-method)
  (set-shape w (make-instance 'empty-shape))
  (install-callbacks w)
  w)

;; TESTs

(defvar *win*)
(setf *win* (make-instance 'window))

(set-shape *win* 
           (let ((c (make-instance 'circle)))
             (set-radius c 100)
             (set-x (center c) 50)
             (set-y (center c) 50)
             (set-color c :red)
             (set-filledp c t)
             c))

(redraw *win*)





(defmethod invalidate ((w window))
  (mg:invalidate (mg-window w))
  w)

(defmethod set-shape ((w window) shape)
  (unless (typep shape 'shape)
    (error "The value shape is not of desired type."))
  (set-window shape w)
  (setf (slot-value w 'shape) shape)
  (invalidate w))

(defmethod set-background ((w window) color)
  (unless (colorp color)
    (error "The value color is not a correct representation of a color. "))
  (setf (slot-value w 'background) color)
  (invalidate w))

(set-shape *win* 
           (let ((c (make-instance 'circle)))
             (set-radius c 80)
             (set-x (center c) 50)
             (set-y (center c) 50)
             (set-color c :green)
             (set-filledp c t)
             c))





;; class MG-OBJECT

(defclass mg-object () 
  ((delegate :initform nil)
   (events :initform '())))

(defmethod delegate ((obj mg-object))
  (slot-value obj 'delegate))

(defmethod set-delegate ((obj mg-object) delegate)
  (setf (slot-value obj 'delegate) delegate))

(defmethod events ((obj mg-object))
  (slot-value obj 'events))

(defun canonicalize-event (event) 
  (if (typep event 'symbol)
      (list event event) 
      event))

(defun canonicalize-events (events)
  (mapcar #'canonicalize-event events))

(defmethod set-events ((object mg-object) value) 
  (setf (slot-value object 'events) 
	(canonicalize-events value))
  object)

(defmethod remove-event ((obj mg-object) event)
  (setf (slot-value obj 'events)
	(remove-if (lambda (l)
		     (eql event (first l))) 
		   (slot-value obj 'events))))

(defmethod add-event ((obj mg-object) event)
  (remove-event obj event)
  (setf (slot-value obj 'events)
	(cons (canonicalize-event event)
	      (events obj))))

(defmethod find-event ((obj mg-object) event)
  (find-if (lambda (ev)
	     (eql event (car ev)))
	   (events obj)))

(defmethod send-event ((object mg-object) event &rest event-args) 
  (when (delegate object)
    (let ((ev (second (find-event object event)))) 
      (when ev 
	(apply ev (delegate object) object event-args)))))



(defmethod changing ((object mg-object) msg &rest args)
  (apply #'send-event object 'ev-changing msg args))

(defmethod change ((object mg-object) msg &rest args)
  (apply #'send-event object 'ev-change msg args))




;; class SHAPE - changes

(defclass shape (mg-object)
  ((color :initform :black)
   (thickness :initform 1)
   (filledp :initform nil)
   (window :initform nil)))

;; dost nepohodlne a neprehledne

(defmethod set-color ((shape shape) value)
  (changing shape 'set-color value)
  (setf (slot-value shape 'color) value)
  (change shape 'set-color value)
  shape)

(defmethod set-thickness ((shape shape) value)
  (changing shape 'set-thickness value)
  (setf (slot-value shape 'thickness) value)
  (change shape 'set-thickness value)
  shape)

;; prvni uprava

(defmethod call-with-change ((object mg-object) function message &rest args)
  (apply #'changing object message args)
  (funcall function)
  (apply #'change object message args)
  object)

(defmethod set-color ((shape shape) value)
  (call-with-change shape
                    (lambda ()
                      (setf (slot-value shape 'color) value))
                    'set-color
                    value))

;; druha uprava - makro

(defmacro with-change ((object message &rest msg-args)
                       &body body)
  `(call-with-change ,object
                     (lambda () ,@body)
                     ,message
                     ,@msg-args))

(defmethod set-color ((shape shape) value)
  (with-change (shape 'set-color value)
    (setf (slot-value shape 'color) value)))

(defmethod set-thickness ((shape shape) value)
  (with-change (shape 'set-thickness value)
    (setf (slot-value shape 'thickness) value)))

(defmethod set-filledp ((shape shape) value)
  (with-change (shape 'set-filledp value)
    (setf (slot-value shape 'filledp) value)))

(defmethod set-x ((point point) value)
  (with-change (point 'set-x value)
    (setf (slot-value point 'x) value)))

(defmethod set-y ((point point) value)
  (with-change (point 'set-y value)
    (setf (slot-value point 'y) value)))

(defmethod set-r-phi ((point point) r phi)
  (with-change (point 'set-r-phi r phi)
    (setf (slot-value point 'x) (* r (cos phi))
          (slot-value point 'y) (* r (sin phi)))))

(defmethod set-r ((point point) r)
  (with-change (point 'set-r r)
    (set-r-phi point r (phi point))))

(defmethod set-phi ((point point) phi)
  (with-change (point 'set-phi phi)
    (set-r-phi point (r point) phi)))

(defmethod set-radius ((c circle) value)
  (with-change (c 'set-radius value)
    (setf (slot-value c 'radius) value)))

;; a tak dale se vsemi podobnymi metodami, co meni stav cehokoli...


;; transformace - zmeny

#|

;; co by bylo fajn
(defmethod move ((shape shape) dx dy)
  (with-change (shape 'move dx dy)
    (call-previous-method)))

(defmethod move ((pt point) dx dy)
  (set-x pt (+ (x pt) dx))
  (set-y pt (+ (y pt) dy))
  pt)

|#

;; jak to realizovat

(defmethod do-move ((shape shape) dx dy)
  shape)

(defmethod move ((shape shape) dx dy)
  (with-change (shape 'move dx dy)
    (do-move shape dx dy)))

(defmethod do-move ((pt point) dx dy)
  (set-x pt (+ (x pt) dx))
  (set-y pt (+ (y pt) dy))
  pt)

(defmethod do-move ((c circle) dx dy)
  (move (center c) dx dy)
  c)

;; a podobne pro dalsi tridy a rotate + do-rotate a scale + do-scale


;; upravy WINDOW

(defclass window (mg-object)
  ((mg-window :initform (mg:display-window))
   shape
   (background :initform :white)))

(defmethod set-shape ((w window) shape)
  (with-change (w 'set-shape shape)
    (setf (slot-value w 'shape) shape)
    (set-window shape w)
    (set-delegate shape w)
    (set-events shape '(ev-change))
    (invalidate w)))

(defmethod set-background ((w window) color)
  (with-change (w 'set-background color)
    (setf (slot-value w 'background) color)
    (invalidate w)))

(defmethod ev-change ((w window) sender message &rest args)
  (invalidate w))


;; TESTs

(setf *win* (make-instance 'window))
(setf *circle* (make-instance 'circle))
(set-shape *win* *circle*)

(set-radius *circle* 40)
(set-filledp *circle* t)
(set-color *circle* :red)
(move *circle* 100 100)
(set-background *win* :blue)

; Proc nefunguje?
(move (center *circle*) 0 -50)





; reseni - uprava slozenych objektu

(defmethod initialize-instance ((c circle) &rest args)
  (call-next-method)
  (let ((center (center c)))
    (set-events center '(ev-changing ev-change))
    (set-delegate center c))
  c)

(defmethod do-set-items ((shape compound-shape) value)
  (setf (slot-value shape 'items) (copy-list value))
  (send-to-items shape #'set-window (window shape))
  (send-to-items shape #'set-delegate shape)
  (send-to-items shape #'set-events '(ev-changing ev-change)))

; preposilani delegatum

(defmethod ev-changing ((c circle) sender message &rest message-args)
  (changing c 'ev-changing sender message message-args))

(defmethod ev-change ((c circle) sender message &rest message-args)
  (change c 'ev-change sender message message-args))

(defmethod ev-changing ((cs compound-shape) sender message &rest message-args)
  (changing cs 'ev-changing sender message message-args))

(defmethod ev-change ((cs compound-shape) sender message &rest message-args)
  (change cs 'ev-change sender message message-args))




;; TEST-DELEGATE

(defclass test-delegate (mg-object)
  ())

(defmethod ev-change ((d test-delegate) sender message &rest args)
  (format
   t
   "~%~%Probehla zmena:~%Objekt ~s~%Metoda: ~s~%Argumenty: ~s"
   sender message args))

(defmethod ev-changing ((d test-delegate) sender message &rest args)
  (format
   t
   "~%~%Probehne zmena:~%Objekt ~s~%Metoda: ~s~%Argumenty: ~s"
   sender message args))

;; TESTs

(defvar *del*)
(setf *del* (make-instance 'test-delegate))
(setf *circle* (make-instance 'circle))
(set-delegate *circle* *del*)
(set-events *circle* '(ev-change ev-changing))

(move *circle* 10 10)



#|
;; dalsi vylepseni

(defclass mg-object ()
  ((delegate :initform nil)
   (events :initform '())
   (change-level :initform 0)))

(defmethod call-without-changes ((object mg-object) function)
  (setf (slot-value object 'change-level)
        (+ (slot-value object 'change-level) 1))
  (unwind-protect
      (funcall function)
    (setf (slot-value object 'change-level)
          (- (slot-value object 'change-level) 1)))
  object)

(defmacro without-changes (object &body body)
  `(call-without-changes ,object (lambda () ,@body)))

(defmethod changing ((object mg-object) msg &rest args)
  (unless (> (slot-value object 'change-level) 0)
    (apply #'send-event object 'ev-changing msg args)))

(defmethod change ((object mg-object) msg &rest args)
  (unless (> (slot-value object 'change-level) 0)
    (apply #'send-event object 'ev-change msg args)))

(defmethod call-with-change
           ((object mg-object) function message &rest args)
  (apply #'changing object message args)
  (without-changes object
    (funcall function))
  (apply #'change object message args)
  object)

;; TESTs

(defvar *del*)
(setf *del* (make-instance 'test-delegate))
(setf *circle* (make-instance 'circle))
(set-delegate *circle* *del*)
(set-events *circle* '(ev-change ev-changing))

(move *circle* 10 10)

|#




;; callback mouse-down

#|

(defvar *mgw*)
(setf *mgw* (mg:display-window))

(mg:set-callback *mgw* :mouse-down
              (lambda (mgw &rest args)
                (declare (ignore mgw))
                (print "mouse-down")
                (print args)))


|#

;; simple objects

(defmethod install-callbacks ((w window))
  (mg:set-callback (slot-value w 'mg-window)
		   :display (lambda (mgw)
                              (declare (ignore mgw))
                              (redraw w)))
  (mg:set-callback 
   (slot-value w 'mg-window) 
   :mouse-down (lambda (mgw button x y)
		 (declare (ignore mgw))
		 (send-mouse-down w button x y)))
  w)

(defmethod send-mouse-down ((w window) button x y)
  (window-mouse-down w
		     button 
		     (move (make-instance 'point) x y)))

(defmethod window-mouse-down ((w window) button position)
  (when (contains-point-p (shape w) position)
    (mouse-down (shape w) button position)))



(defmethod mouse-down ((shape shape) button position)
  (send-event shape 'ev-mouse-down shape button position))

; hit-testing

(defmethod contains-point-p ((shape shape) point)
  nil)



(defun sqr (x)
  (expt x 2))

(defun point-sq-dist (pt1 pt2)
  (+ (sqr (- (x pt1) (x pt2)))
     (sqr (- (y pt1) (y pt2)))))

(defmethod contains-point-p ((shape point) point)
  (<= (point-sq-dist shape point) 
      (sqr (thickness shape))))



(defmethod contains-point-p ((circle circle) point)
  (let ((sq-dist (point-sq-dist (center circle) point)))
    (and (if (filledp circle)
             t
           (<= (sqr (- (radius circle) 
		       (/ (thickness circle) 2))) 
	       sq-dist))
         (<= sq-dist (sqr (radius circle))))))



(defmethod contains-point-p ((pic picture) point)
  (find-if (lambda (it)
	     (contains-point-p it point))
	   (items pic)))



(defun scalar-product (v1 v2)
  (apply #'+ (mapcar #'* v1 v2)))

(defun scalar-mult (k v)
  (mapcar (lambda (x) (* k x))
          v))

(defun vec-+ (v1 &rest vectors)
  (apply #'mapcar #'+ v1 vectors))

(defun vec-- (v1 &rest vectors)
  (apply #'mapcar #'- v1 vectors))

(defun vec-= (v1 v2)
  (every #'= v1 v2))

(defun vec-sq-len (v)
  (scalar-product v v))

(defun vec-near-p (v1 v2 tolerance)
  (<= (vec-sq-len (vec-- v1 v2))
      (expt tolerance 2)))

(defun pt-in-seg-p (pt x1 x2 tolerance)
  "Zjisti, zda je bod pt na usecce [x1 x2]."
  (let* ((u (vec-- x2 x1))
         (v (vec-- x1 pt))
         (uu (scalar-product u u)))
    (if (zerop uu)
        (vec-near-p pt x1 tolerance)
      (let ((k (- (/ (scalar-product u v) uu))))
        (and (<= 0 k 1)
             (vec-near-p pt (vec-+ x1 (scalar-mult k u)) tolerance))))))

(defun point-in-segs-p (pt tolerance &optional pt1 pt2 &rest points)
  (and pt1 
       pt2
       (or (pt-in-seg-p pt pt1 pt2 tolerance)
           (apply #'point-in-segs-p pt tolerance pt2 points))))

(defun vert-between-p (pt pt1 pt2)
  (let ((pty (second pt))
        (pt1y (second pt1))
        (pt2y (second pt2)))
    (declare (number pty pt1y pt2y))
    (declare (optimize (speed 3) (safety 0)))
    (or (< pt1y pty pt2y)
        (> pt1y pty pt2y)
        ;;u mensiho z pt1y, pt2y umoznime i rovnost
        (and (/= pt1y pt2y)
             (= (min pt1y pt2y) pty)))))

(defun horiz-right-p (pt pt1 pt2)
  (destructuring-bind (ptx pty pt1x pt1y pt2x pt2y) (append pt pt1 pt2)
    (< (+ (* (- pt1x pt2x) 
             (/ (- pty pt2y)
                (- pt1y pt2y)))
          pt2x)
       ptx)))

(defun intersects-p (pt pt1 pt2)
  (and (vert-between-p pt pt1 pt2)
       (horiz-right-p pt pt1 pt2)))

(defun count-intersections (pt &optional pt1 pt2 &rest points)
  (if (and pt1 pt2)
       (+ (if (intersects-p pt pt1 pt2) 1 0)
          (apply #'count-intersections pt pt2 points))
    0))

(defun point-in-poly-p (pt ignore &rest points)
  (declare (ignore ignore))
  (oddp (apply #'count-intersections pt points)))

(defun point-x-y (point)
  (list (x point) (y point)))

(defmethod contains-point-p ((poly polygon) point)
  (let ((items (items poly)))
    (apply (if (filledp poly) #'point-in-poly-p #'point-in-segs-p) 
           (point-x-y point)
           (thickness poly)
           (mapcar #'point-x-y (if (or (closedp poly) (filledp poly))
                                  (append (last items) items)
                                items)))))



(defmethod contains-point-p ((shape full-shape) point)
  t)


;; UKAZKY

; moznost 1

(defun random-color ()
  (let ((colors (color:get-all-color-names)))
    (nth (random (length colors)) colors)))

(defclass my-window (window) ())

(defmethod ev-mouse-down ((w my-window) sender origin button position)
  (set-color origin (random-color)))

#|

(defvar *win*)
(setf *win* (make-instance 'my-window))
(setf *circle* (make-instance 'circle))
(move *circle* 100 100)
(set-radius *circle* 40)
(set-filledp *circle* t)
(set-shape *win* *circle*)
(add-event *circle* 'ev-mouse-down)

|#

; moznost 2

(defclass click-circle (circle) ())

(defmethod mouse-down ((circ click-circle) button position)
  (set-color circ (random-color))
  (call-next-method))

#|

(setf *win* (make-instance 'window))
(setf *circle* (make-instance 'click-circle))
(move *circle* 100 100)
(set-radius *circle* 40)
(set-filledp *circle* t)
(set-shape *win* *circle*)

|#

;; UKAZKA - slozitejsi objekt

#|

(setf *win* (make-instance 'my-window))
(setf *circle* (make-instance 'circle))
(move *circle* 120 100)
(set-radius *circle* 40)
(set-filledp *circle* t)

(defvar *poly*)
(setf *poly* (make-instance 'polygon))
(set-items *poly*`(,(make-point 20 20) 
                   ,(make-point 150 20)
                   ,(make-point 150 120) 
                   ,(make-point 20 120)))
(set-filledp *poly* t)
(set-closedp *poly* t)

(defvar *pic*)
(setf *pic* (make-instance 'picture))
(set-items *pic* (list *circle* *poly*))

(set-shape *win* *pic*)
(set-shape *win* *poly*)
(set-shape *win* *circle*)

(add-event *pic* 'ev-mouse-down)
(add-event *poly* 'ev-mouse-down)
(add-event *circle* 'ev-mouse-down)

(set-propagate-color-p *pic* t)

|#

;; compound object - picture

#|

(defmethod mouse-down ((p picture) button position)
  (let ((item (find-if (lambda (it)
                         (contains-point-p it position))
                       (items p))))
    (when item
      (mouse-down item button position))))

(defmethod ev-mouse-down ((p picture) sender origin button position)
  (send-event p 'ev-mouse-down origin button position))

(defmethod do-set-items ((p picture) items)
  (call-next-method)
  (when (propagate-color-p p)
    (send-to-items-set-color p (color p)))
  (send-to-items p #'add-event 'ev-mouse-down)
  items)

|#

; UKAZKA

#|

(setf *win* (make-instance 'my-window))
(setf *circle* (make-instance 'circle))
(move *circle* 120 100)
(set-radius *circle* 40)
(set-filledp *circle* t)

(defvar *poly*)
(setf *poly* (make-instance 'polygon))
(set-items *poly*`(,(make-point 20 20) 
                   ,(make-point 150 20)
                   ,(make-point 150 120) 
                   ,(make-point 20 120)))
(set-filledp *poly* t)
(set-closedp *poly* t)

(defvar *pic*)
(setf *pic* (make-instance 'picture))
(set-items *pic* (list *circle* *poly*))

(set-shape *win* *pic*)
(add-event *pic* 'ev-mouse-down)

(set-items *pic* (list *poly* *circle*))

|#


