;;; Objektova nastavba

(load (current-pathname "micro-graphics/load.lisp"))

;; auxiliary functions
(defun colorp (color) (if (find color (color:get-all-color-names)) t nil))

;; class WINDOW --------------------------------------------------------------------
(defclass window ()
  ((mg-window :initform (mg:display-window :height 220 :width 440))
   (shape :initform nil)
   (background :initform :white)))

; reading and setting slot values
(defmethod mg-window ((window window)) (slot-value window 'mg-window))

(defmethod shape ((w window)) (slot-value w 'shape))

(defmethod set-shape ((w window) shape)
  (unless (or (typep shape 'point)
              (typep shape 'circle)
              (typep shape 'picture)
              (typep shape 'polygon))
    (error "The value shape is not of desired type."))
  (set-window shape w)
  (setf (slot-value w 'shape) shape)
  w)

(defmethod background ((w window)) (slot-value w 'background))

(defmethod set-background ((w window) color)
  (unless (colorp color)
    (error "The value color is not a correct representation of a color. "))
  (setf (slot-value w 'background) color)
  w)

; drawing methods
(defmethod redraw ((window window))
  (let ((mgw (slot-value window 'mg-window)))
    (mg:set-param mgw :background (background window))
    (mg:clear mgw)
    (when (shape window)
      (draw (shape window))))
  window)

;; class POINT --------------------------------------------------------------------
(defclass point () 
  ((x :initform 0) 
   (y :initform 0)
   (color :initform :black) 
   (thickness :initform 1) 
   (window :initform nil)))

; reading and setting slot values
(defmethod x ((point point)) (slot-value point 'x))
(defmethod y ((point point)) (slot-value point 'y))
(defmethod set-x ((point point) value)
  (unless (typep value 'number)
    (error "x coordinate of a point should be a number. "))
  (setf (slot-value point 'x) value)
  point)
(defmethod set-y ((point point) value)
  (unless (typep value 'number)
    (error "y coordinate of a point should be a number. "))
  (setf (slot-value point 'y) value)
  point)

; polar coordinates
(defmethod r ((point point))
  (let ((x (slot-value point 'x))
        (y (slot-value point 'y)))
    (sqrt (+ (* x x) (* y y)))))

(defmethod phi ((point point))
  (let ((x (slot-value point 'x))
        (y (slot-value point 'y)))
    (cond ((> x 0) (atan (/ y x)))
          ((< x 0) (+ pi (atan (/ y x))))
          (t (* (signum y) (/ pi 2))))))

(defmethod set-r-phi ((point point) r phi)
  (setf (slot-value point 'x) (* r (cos phi))
        (slot-value point 'y) (* r (sin phi)))
  point)

(defmethod set-r ((point point) r) (set-r-phi point r (phi point)))
(defmethod set-phi ((point point) phi) (set-r-phi point (r point) phi))

; function for easy construction
(defun make-point (x y)
  (let ((point (make-instance 'point)))
    (set-x point x)
    (set-y point y)
    point))

; reading and setting drawing parametr slots
(defmethod color ((pt point))  (slot-value pt 'color)) 

(defmethod set-color ((pt point) value) 
  (unless (colorp value)
    (error "The value is not a correct representation of a color. "))
  (setf (slot-value pt 'color) value)
  pt) 

(defmethod thickness ((pt point)) (slot-value pt 'thickness)) 

(defmethod set-thickness ((pt point) value) 
  (unless (and (typep value 'integer) (< 0 value))
    (error "The value should be a positive integer. "))
  (setf (slot-value pt 'thickness) value)
  pt) 

(defmethod window ((pt point)) (slot-value pt 'window))

(defmethod set-window ((pt point) value) 
  (unless (typep value 'window)
    (error "The value should be a window. "))
  (setf (slot-value pt 'window) value)
  pt)

(defmethod shape-mg-window ((pt point))
  (when (window pt)
    (mg-window (window pt))))

; drawing methods
(defmethod set-mg-params ((pt point)) 
  (let ((mgw (shape-mg-window pt)))
    (mg:set-param mgw :foreground (color pt)) 
    (mg:set-param mgw :filledp t))
  pt)

(defmethod do-draw ((pt point)) 
  (mg:draw-circle (shape-mg-window pt) 
                  (x pt) 
                  (y pt) 
                  (thickness pt))
  pt)

(defmethod draw ((pt point))
  (set-mg-params pt)
  (do-draw pt))

;; class CIRCLE --------------------------------------------------------------------
(defclass circle () 
  ((center :initform (make-instance 'point)) 
   (radius :initform 1)
   (color :initform :black)
   (thickness :initform 1)
   (filledp :initform nil)
   (window :initform nil)))

; reading and setting slot values
(defmethod radius ((c circle)) (slot-value c 'radius))

(defmethod set-radius ((c circle) value)
  (when (< value 0)
    (error "Circle radius should be a non-negative number"))
  (setf (slot-value c 'radius) value)
  c)

(defmethod center ((c circle)) (slot-value c 'center))

; reading and setting drawing parametr slots
(defmethod color ((c circle))  (slot-value c 'color)) 

(defmethod set-color ((c circle) value) 
  (unless (colorp value)
    (error "The value is not a correct representation of a color. "))
  (setf (slot-value c 'color) value)
  c) 

(defmethod thickness ((c circle))  (slot-value c 'thickness)) 

(defmethod set-thickness ((c circle) value) 
  (unless (and (typep value 'integer) (< 0 value))
    (error "The value should be a positive integer. "))
  (setf (slot-value c 'thickness) value)
  c) 

(defmethod filledp ((c circle)) (slot-value c 'filledp))

(defmethod set-filledp ((c circle) value) (setf (slot-value c 'filledp) value) c)

(defmethod window ((c circle)) (slot-value c 'window))

(defmethod set-window ((c circle) value) 
  (unless (typep value 'window)
    (error "The value should be a window. "))
  (setf (slot-value c 'window) value)
  c)

(defmethod shape-mg-window ((c circle))
  (when (window c)
    (mg-window (window c))))

; drawing methods 
(defmethod set-mg-params ((c circle))
  (let ((mgw (shape-mg-window c)))
    (mg:set-param mgw :foreground (color c))
    (mg:set-param mgw :thickness (thickness c))
    (mg:set-param mgw :filledp (filledp c)))
  c)

(defmethod do-draw ((c circle))
  (mg:draw-circle (shape-mg-window c)
                  (x (center c))
                  (y (center c))
                  (radius c))
  c)

(defmethod draw ((c circle))
  (set-mg-params c)
  (do-draw c))



;; class PICTURE --------------------------------------------------------------------
(defclass picture ()
  ((items :initform '())
   (window :initform nil)))

; reading and setting slot values
(defmethod items ((pic picture)) (copy-list (slot-value pic 'items)))

(defmethod set-items ((pic picture) value)
  (unless (every (lambda (elem)
                   (or (typep elem 'point)
                       (typep elem 'circle)
                       (typep elem 'picture)
                       (typep elem 'polygon)))
                 value)
    (error "Picture elements are not of desired type."))
  (setf (slot-value pic 'items) (copy-list value))
  (when (window pic) (set-window pic (window pic)))
  pic)

; reading and setting drawing parametr slots
(defmethod window ((shape picture)) (slot-value shape 'window))
 
(defmethod set-window ((shape picture) value)
  (unless (typep value 'window)
    (error "The value should be a window. "))
  (dolist (item (items shape))
    (set-window item value))
  (setf (slot-value shape 'window) value)
  shape)

(defmethod shape-mg-window ((shape picture))
  (when (window shape)
    (mg-window (window shape))))

; drawing method
(defmethod draw ((pic picture))
  (dolist (item (reverse (items pic)))
    (draw item))
  pic)

;; class POLYGON --------------------------------------------------------------------
(defclass polygon ()
  ((items :initform '())
   (color :initform :black)
   (thickness :initform 1)
   (filledp :initform nil)
   (closedp :initform t)
   (window :initform nil)))

; reading and setting slot values
(defmethod items ((poly polygon))  (copy-list (slot-value poly 'items)))

(defmethod set-items ((poly polygon) value) 
  (unless (every (lambda (elem) 
                   (typep elem 'point)) 
                 value)
    (error "Items (value) should be a list of points. "))
  (setf (slot-value poly 'items) (copy-list value))
  poly)

; reading and setting drawing parametr slots
(defmethod color ((p polygon)) (slot-value p 'color))

(defmethod set-color ((p polygon) value)
  (unless (colorp value)
    (error "The value is not a correct representation of a color. "))
  (setf (slot-value p 'color) value)
  p)

(defmethod thickness ((p polygon)) (slot-value p 'thickness))

(defmethod set-thickness ((p polygon) value)
  (unless (and (typep value 'integer) (< 0 value))
    (error "The value should be a positive integer. "))
  (setf (slot-value p 'thickness) value)
  p)

(defmethod closedp ((p polygon)) (slot-value p 'closedp))

(defmethod set-closedp ((p polygon) value) (setf (slot-value p 'closedp) value) p)

(defmethod filledp ((p polygon)) (slot-value p 'filledp))

(defmethod set-filledp ((p polygon) value) (setf (slot-value p 'filledp) value) p)

(defmethod window ((p polygon)) (slot-value p 'window))

(defmethod set-window ((p polygon) value)
  (unless (typep value 'window)
    (error "The value should be a window. "))
  (setf (slot-value p 'window) value)
  p)

(defmethod shape-mg-window ((shape polygon))
  (when (window shape)
    (mg-window (window shape))))

; drawing methods
(defmethod set-mg-params ((poly polygon)) 
  (let ((mgw (shape-mg-window poly)))
    (mg:set-param mgw :foreground (color poly)) 
    (mg:set-param mgw :thickness (thickness poly)) 
    (mg:set-param mgw :filledp (filledp poly))
    (mg:set-param mgw :closedp (closedp poly)))
  poly)

(defmethod do-draw ((poly polygon)) 
  (let (coordinates)
    (dolist (point (reverse (items poly)))
      (setf coordinates (cons (y point) coordinates))
      (setf coordinates (cons (x point) coordinates)))
    (mg:draw-polygon (shape-mg-window poly) 
                     coordinates))
  poly)

(defmethod draw ((poly polygon)) 
  (set-mg-params poly) 
  (do-draw poly))

;; Pozor - musi se upravit set-items u picture a set-shape u window.

;;; Transformations
(defmethod move ((pt point) dx dy)
  (set-x pt (+ (x pt) dx))
  (set-y pt (+ (y pt) dy))
  pt)

(defmethod rotate ((pt point) angle center)
  (let ((cx (x center))
        (cy (y center)))
    (move pt (- cx) (- cy))
    (set-phi pt (+ (phi pt) angle))
    (move pt cx cy)
    pt))

(defmethod scale ((pt point) coeff center)
  (let ((cx (x center))
        (cy (y center)))
    (move pt (- cx) (- cy))
    (set-r pt (* (r pt) coeff))
    (move pt cx cy)
    pt))

(defmethod move ((c circle) dx dy)
  (move (center c) dx dy)
  c)

(defmethod rotate ((c circle) angle center)
  (rotate (center c) angle center)
  c)

(defmethod scale ((c circle) coeff center)
  (scale (center c) coeff center)
  (set-radius c (* (radius c) coeff))
  c)

(defmethod move ((pic picture) dx dy)
  (dolist (it (items pic))
    (move it dx dy))
  pic)

(defmethod rotate ((pic picture) angle center)
  (dolist (it (items pic))
    (rotate it angle center))
  pic)

(defmethod scale ((pic picture) coeff center)
  (dolist (item (items pic))
    (scale item coeff center))
  pic)

(defmethod move ((poly polygon) dx dy)
  (dolist (it (items poly))
    (move it dx dy))
  poly)

(defmethod rotate ((poly polygon) angle center)
  (dolist (it (items poly))
    (rotate it angle center))
  poly)

(defmethod scale ((poly polygon) coeff center)
  (dolist (item (items poly))
    (scale item coeff center))
  poly)


;;---------------------------------------------------------------------------------------------------

(defvar *win1* (make-instance 'window))
(set-background *win1* :lightblue)


;; TVARY ;;
(defvar *bily* (make-instance 'polygon))
(set-color (set-filledp (set-closedp *bily* t) t) :white)
(set-items *bily* (list (make-point 100 5)
                        (make-point 0 180)
                        (make-point 200 180)))

(defvar *cerny*)
(setf *cerny* (make-instance 'polygon))
(set-color (set-filledp (set-closedp *cerny* t) nil) :black)
(set-thickness *cerny* 2)
(set-items *cerny* (list (make-point 100 5)
                      (make-point 0 180)
                      (make-point 200 180)))

(defvar *cerveny* (make-instance 'polygon))
(set-color (set-filledp (set-closedp *cerveny* t) nil) :red)
(set-thickness *cerveny* 16)
(set-items *cerveny* (list (make-point 100 30)
                      (make-point 21 168)
                      (make-point 179 168)))

(defvar *kriz* (make-instance 'polygon))
(set-color (set-filledp (set-closedp *kriz* t) t) :black)
(set-items *kriz* (list (make-point 100 117)
                      (make-point 120 97)
                      (make-point 130 107)
                      (make-point 110 127)
                      (make-point 130 147)
                      (make-point 120 157)
                      (make-point 100 137)
                      (make-point 80 157)
                      (make-point 70 147)
                      (make-point 90 127)
                      (make-point 70 107)
                      (make-point 80 97)))

(defvar *sipka1* (make-instance 'polygon))
(set-color (set-filledp (set-closedp *sipka1* t) t) :black)
(set-items *sipka1* (list (make-point 100 105)
                      (make-point 110 90)
                      (make-point 120 105)
                      (make-point 114 105)
                      (make-point 114 155)
                      (make-point 106 155)
                      (make-point 106 105)))

(defvar *sipka2* (make-instance 'polygon))
(set-color (set-filledp (set-closedp *sipka2* t) t) :black)
(set-items *sipka2* (list (make-point 100 105)
                      (make-point 110 90)
                      (make-point 120 105)
                      (make-point 114 105)
                      (make-point 114 155)
                      (make-point 106 155)
                      (make-point 106 105)))
(move (rotate *sipka2* pi (make-point 100 122)) 0 0)


(defvar *obdelnik1* (make-instance 'polygon))
(set-color (set-filledp (set-closedp *obdelnik1* t) t) :black)
(set-items *obdelnik1* (list (make-point 82 90)
                             (make-point 97 90)
                             (make-point 97 157)
                             (make-point 82 157)))

(defvar *obdelnik2* (make-instance 'polygon))
(set-color (set-filledp (set-closedp *obdelnik2* t) t) :black)
(set-items *obdelnik2* (list (make-point 103 90)
                             (make-point 118 90)
                             (make-point 118 110)
                             (make-point 128 130)
                             (make-point 128 157)
                             (make-point 113 157)
                             (make-point 113 130)
                             (make-point 103 110)))

(defvar *circ-bily* (make-instance 'circle))
(set-x (set-y (center *circ-bily*) 90) 90)
(set-radius *circ-bily* 70)
(set-color (set-filledp *circ-bily* t) :white)

(defvar *circ-modry* (make-instance 'circle))
(set-x (set-y (center *circ-modry*) 90) 90)
(set-radius *circ-modry* 60)
(set-color (set-filledp *circ-modry* t) :blue)

(defvar *circ-cerny* (make-instance 'circle))
(set-x (set-y (center *circ-cerny*) 90) 90)
(set-radius *circ-cerny* 70)
(set-color (set-filledp *circ-cerny* nil) :black)
(set-thickness *circ-cerny* 2)

(defvar *circ-cerveny* (make-instance 'circle))
(set-x (set-y (center *circ-cerveny*) 90) 90)
(set-radius *circ-cerveny* 57)
(set-color (set-filledp *circ-cerveny* nil) :red)
(set-thickness *circ-cerveny* 18)

(defvar *circ-cerveny-f* (make-instance 'circle))
(set-x (set-y (center *circ-cerveny-f*) 90) 90)
(set-radius *circ-cerveny-f* 66)
(set-color (set-filledp *circ-cerveny-f* t) :red)

(defvar *bily-obdelnik* (make-instance 'polygon))
(set-color (set-filledp (set-closedp *bily-obdelnik* t) t) :white)
(set-items *bily-obdelnik* (list (make-point 36 80)
                             (make-point 144 80)
                             (make-point 144 100)
                             (make-point 36 100)))
  

(defvar *cerveny-obdelnik1* (make-instance 'polygon))
(set-color (set-filledp (set-closedp *cerveny-obdelnik1* t) t) :red)
(set-items *cerveny-obdelnik1* (list (make-point 123 43)
                             (make-point 137 57)
                             (make-point 57 137)
                             (make-point 43 123)))

(defvar *cerveny-obdelnik2* (make-instance 'polygon))
(set-color (set-filledp (set-closedp *cerveny-obdelnik2* t) t) :red)
(set-items *cerveny-obdelnik2* (list (make-point 123 43)
                             (make-point 137 57)
                             (make-point 57 137)
                             (make-point 43 123)))
(rotate *cerveny-obdelnik2* (/ pi 2) (make-point 90 90))
                  

;; ZNACKY ;;
(defvar *znacka1* (make-instance 'picture))
(set-items *znacka1* (list *cerny* *cerveny* *kriz* *bily*))

(defvar *znacka2* (make-instance 'picture))
(set-items *znacka2* (list *cerny* *cerveny* *sipka1* *sipka2* *bily*))

(defvar *znacka3* (make-instance 'picture))
(set-items *znacka3*  (list *cerny* *cerveny* *bily*))
;; (rotate *znacka3* pi (make-point 100 93))

(defvar *znacka4* (make-instance 'picture))
(set-items *znacka4*  (list *cerny* *cerveny* *obdelnik1* *obdelnik2* *bily*))

(defvar *znacka5* (make-instance 'picture))
(set-items *znacka5*  (list *circ-cerveny* *circ-cerny* *circ-bily*))

(defvar *znacka6* (make-instance 'picture))
(set-items *znacka6*  (list *bily-obdelnik* *circ-cerveny-f* *circ-cerny* *circ-bily*))

(defvar *znacka7* (make-instance 'picture))
(set-items *znacka7*  (list *cerveny-obdelnik2* *cerveny-obdelnik1* *circ-cerny* *circ-cerveny* *circ-modry* *circ-bily*))



(defvar *znacky1* (make-instance 'picture))
(set-items *znacky1* (list *znacka5* (move *znacka4* 200 0)))


;; TEST ;;
(set-shape *win1* *znacky1*)
(redraw *win1*)