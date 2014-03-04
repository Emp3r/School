(defclass point () (x y))

(defvar *p1* (make-instance 'point))


(slot-value *p1* 'y)

(setf (slot-value *p1* 'x) 10)
(slot-value *p1* 'x)

(typep *p1* 'point) 


(defmethod r ((point point))
  (let ((x (slot-value point 'x))
        (y (slot-value point 'y)))
    (sqrt (+ (* x x) (* y y)))))

(r *p1*)

(defmethod phi ((point point))
  (let ((x (slot-value point 'x))
        (y (slot-value point 'y)))
    (cond ((> x 0) (atan (/ y x)))
          ((< x 0) (+ pi (atan (/ y x))))
          (t (* (signum y) (/ pi 2))))))

(phi *p1*)

(defmethod set-r-phi ((point point) r phi)
  (setf (slot-value point 'x) (* r (cos phi))
        (slot-value point 'y) (* r (sin phi)))
  point)

(defmethod set-r ((point point) r)
  (set-r-phi point r (phi point)))

(defmethod set-phi ((point point) phi)
  (set-r-phi point (r point) phi))

(set-r *p1* 10)
(set-phi *p1* pi)


(r (make-instance 'point))

(defclass point ()
  ((x :initform 0)
   (y :initform 0)))

(defvar *p2* (make-instance 'point))

