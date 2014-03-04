;;; kod z minule prednasky

;; class POINT

(defclass point ()
  ((x :initform 0)
   (y :initform 0)))

;reading polar coordinates

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

; setting polar coordinates

(defmethod set-r-phi ((point point) r phi)
  (setf (slot-value point 'x) (* r (cos phi))
        (slot-value point 'y) (* r (sin phi)))
  point)

(defmethod set-r ((point point) r)
  (set-r-phi point r (phi point)))

(defmethod set-phi ((point point) phi)
  (set-r-phi point (r point) phi))


;; class CIRCLE

(defclass circle ()
  ((center :initform (make-instance 'point))
   (radius :initform 1)))


;;; nove - motivace k zapouzdreni
 
;; 1

(defvar *point* (make-instance 'point))
(setf (slot-value *point* 'x) 10)
(setf (slot-value *point* 'y) nil)
(r *point*)

(setf (slot-value *point* 'y) 0)

(set-phi *point* nil)

;; 2

(defvar *circle* (make-instance 'circle))
(setf (slot-value *circle* 'radius) -1)

;; 3 

(setf (slot-value *point* 'x) 3)
(setf (slot-value *point* 'y) 4)

(set-r-phi *point* 5 (/ pi 2))

;; 4 - zmena vnitrni reprezentace

(defclass point ()
  ((r :initform 0)
   (phi :initform 0)))



;;; prepracovane tridy

;; class POINT

(defclass point ()
  ((x :initform 0)
   (y :initform 0)))

; reading and setting slot values

(defmethod x ((point point))
  (slot-value point 'x))

(defmethod y ((point point))
  (slot-value point 'y))

(defmethod set-x ((point point) value)
  (unless (typep value 'number)
    (error "x coordinate of a point should be a number"))
  (setf (slot-value point 'x) value)
  point)

(defmethod set-y ((point point) value)
  (unless (typep value 'number)
    (error "y coordinate of a point should be a number"))
  (setf (slot-value point 'y) value)
  point)

; polar coordinates - no changes

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

(defmethod set-r ((point point) r)
  (set-r-phi point r (phi point)))

(defmethod set-phi ((point point) phi)
  (set-r-phi point (r point) phi))



;; class CIRCLE

(defclass circle ()
  ((center :initform (make-instance 'point))
   (radius :initform 1)))

; reading and setting slot values

(defmethod radius ((c circle))
  (slot-value c 'radius))

(defmethod set-radius ((c circle) value)
  (when (< value 0)
    (error "Circle radius should be a non-negative number"))
  (setf (slot-value c 'radius) value)
  c)

(defmethod center ((c circle))
  (slot-value c 'center))



;; priklad nastaveni stredu

(defvar *circ* (make-instance 'circle))
(set-x (center *circ*) 10)


;; class PICTURE

(defclass picture ()
  ((items :initform '())))

(defmethod items ((pic picture))
  (slot-value pic 'items))

(defmethod set-items ((pic picture) value)
  (unless (every (lambda (elem)
                   (or (typep elem 'point)
                       (typep elem 'circle)
                       (typep elem 'picture)))
                 value)
    (error "Picture elements are not of the desired type."))
  (setf (slot-value pic 'items) value)
  pic)



;; priklad - mozne problemy

(defvar *list* (list 0 0 0))
(defvar *pic* (make-instance 'picture))
(set-items *pic* *list*)

(setf *list* (list (make-instance 'point) (make-instance 'circle)))
(set-items *pic* *list*)
(items *pic*)

(setf (first *list*) 0)
(items *pic*)



;; reseni - uprava metod

(defmethod items ((pic picture))
  (copy-list (slot-value pic 'items)))

(defmethod set-items ((pic picture) value)
  (unless (every (lambda (elem)
                   (or (typep elem 'point)
                       (typep elem 'circle)
                       (typep elem 'picture)))
                 value)
    (error "Picture elements are not of desired type."))
  (setf (slot-value pic 'items) (copy-list value))
  pic)

;; test

(setf *list* (list (make-instance 'point) (make-instance 'circle)))
(set-items *pic* *list*)
(items *pic*)

(setf (first *list*) 0)
(items *pic*)
*list*



;; snadnejsi vytvareni a naplneni instanci

(defun make-point (x y)
  (set-x (set-y (make-instance 'point) y) x))

(make-point 4 3)

;; prehlednejsi varianta
(defun make-point (x y)
  (let ((point (make-instance 'point)))
    (set-x point x)
    (set-y point y)
    point))

(make-point 4 3)
