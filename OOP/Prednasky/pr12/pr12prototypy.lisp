; vytvoreni objektu

(defun make-object ()
  (list 'object))

; priklad objektu

;(OBJECT :SCALE funkce-1 :ROTATE funkce-2 :MOVE funkce-3
;        :SET-PHI funkce-4 :SET-R funkce-5
;        :GET-PHI funkce-6 :GET-R funkce-7
;        :Y 0 :X 0
;        :SET-Y funkce-8 :SET-X funkce-9
;        :GET-Y funkce-10 :GET-X funkce-11)


; pristup k polozkam (metodam a slotum)

(defun field-value (obj field)
  (getf (cdr obj) field))

(defun set-field-value (obj field value)
  (setf (getf (cdr obj) field) value))

; posilani zprav

(defun send (obj message &rest args)
  (apply (field-value obj message) obj args))

; definice metod

(defmacro define-method (obj message lambda-list &body body)
  `(set-field-value ,obj ,message (lambda (self ,@lambda-list)
                                    ,@body)))

;  priklad - point

(defvar *point*)
(setf *point* (make-object))

(define-method *point* :get-x ()
  (field-value self :x))

(define-method *point* :get-y ()
  (field-value self :y))

(define-method *point* :set-x (value)
  (set-field-value self :x value))

(define-method *point* :set-y (value)
  (set-field-value self :y value))

(send *point* :set-x 0)
(send *point* :set-y 0)

; test
(send *point* :get-x)
(send *point* :get-y)

(define-method *point* :get-r ()
  (let ((x (send self :get-x))
        (y (send self :get-y)))
    (sqrt (+ (* x x) (* y y)))))

(define-method *point* :get-phi ()
  (let ((x (send self :get-x))
        (y (send self :get-y)))
    (cond ((plusp x) (atan (/ y x)))
          ((minusp x) (+ pi (atan (/ y x))))
          (t (* (signum y) (/ pi 2))))))

(defun set-r-phi (obj r phi)
  (send obj :set-x (* r (cos phi)))
  (send obj :set-y (* r (sin phi))))

(define-method *point* :set-r (value)
  (set-r-phi self value (send self :get-phi))
  value)

(define-method *point* :set-phi (value)
  (set-r-phi self (send self :get-r) value)
  value)

(define-method *point* :move (dx dy)
  (send self :set-x (+ (send self :get-x) dx))
  (send self :set-y (+ (send self :get-y) dy))
  self)

(define-method *point* :rotate (angle)
  (send self :set-phi (+ (send self :get-phi) angle))
  self)

(define-method *point* :scale (coeff)
  (send self :set-r (* (send self :get-r) coeff))
  self)

;(send *point* :move 5 10)

; vytvoreni druheho bodu

(define-method *point* :copy ()
  (copy-list self))

(defvar *a*)
(setf *a* (send *point* :copy))

(send *a* :set-x 5)
(send *point* :get-x)
(send *a* :get-x)

; uprava objektoveho systemu

(defvar *object*)
(setf *object* (make-object))

(define-method *object* :copy ()
  (copy-list self))

; pridavani funkcnosti pomoci kopirovani

(defvar *shape*)
(setf *shape* (send *object* :copy))

(define-method *shape* :get-color ()
  (field-value self :color))

(define-method *shape* :set-color (value)
  (set-field-value self :color value))

(send *shape* :set-color :black)

; novy prototyp bodu - vyuziti kopirovani

(setf *point* (send *shape* :copy))

(define-method *point* :get-x ()
  (field-value self :x))

(define-method *point* :get-y ()
  (field-value self :y))

(define-method *point* :set-x (value)
  (set-field-value self :x value))

(define-method *point* :set-y (value)
  (set-field-value self :y value))

(send *point* :set-x 0)
(send *point* :set-y 0)

(define-method *point* :get-r ()
  (let ((x (send self :get-x))
        (y (send self :get-y)))
    (sqrt (+ (* x x) (* y y)))))

(define-method *point* :get-phi ()
  (let ((x (send self :get-x))
        (y (send self :get-y)))
    (cond ((plusp x) (atan (/ y x)))
          ((minusp x) (+ pi (atan (/ y x))))
          (t (* (signum y) (/ pi 2))))))

(define-method *point* :set-r (value)
  (set-r-phi self value (send self :get-phi))
  value)

(define-method *point* :set-phi (value)
  (set-r-phi self (send self :get-r) value)
  value)

(define-method *point* :move (dx dy)
  (send self :set-x (+ (send self :get-x) dx))
  (send self :set-y (+ (send self :get-y) dy))
  self)

(define-method *point* :rotate (angle)
  (send self :set-phi (+ (send self :get-phi) angle))
  self)

(define-method *point* :scale (coeff)
  (send self :set-r (* (send self :get-r) coeff))
  self)

; circle

(defvar *circle*)
(setf *circle* (send *shape* :copy))

(set-field-value *circle* :center (send *point* :copy))
(set-field-value *circle* :radius 0)

(define-method *circle* :get-center ()
  (field-value self :center))

(define-method *circle* :get-radius ()
  (field-value self :radius))

(define-method *circle* :set-radius (value)
  (set-field-value self :radius value))

(define-method *circle* :move (dx dy)
  (send (send self :get-center) :move dx dy)
  self)

(define-method *circle* :rotate (angle)
  (send (send self :get-center) :rotate angle)
  self)

(define-method *circle* :scale (coeff)
  (send (send self :get-center) :scale coeff)
  (send self :set-radius (* coeff (send self :get-radius)))
  self)

; priklad - testovani funkcnosti kruhu

(setf circ1 (send *circle* :copy))
(setf circ2 (send *circle* :copy))

(send circ1 :set-radius 5)
(send circ2 :set-radius 10)

(send circ1 :get-radius)
(send circ2 :get-radius)

(send (send circ1 :get-center) :get-x)
(send (send circ2 :get-center) :get-x)

(send circ1 :move 20 20)

(send (send circ1 :get-center) :get-x)
(send (send circ2 :get-center) :get-x)


; oprava chyby - dedicnost

(setf *object* (make-object))
(set-field-value *object* :super-object nil)

(defun super-object (obj)
  (field-value obj :super-object))

(define-method *object* :super ()
  (super-object self))

; uprava
(defun field-value (obj field)
  (let* ((field-not-found '#:field-not-found)
         (result (getf (cdr obj) field field-not-found)))
    (if (eql result field-not-found)
        (field-value (field-value obj :super-object) field)
      result)))

; klonovani

(defun clone-object (obj)
  (list 'object :super-object obj))

(define-method *object* :clone ()
  (clone-object self))

; volani prepsane metody

(defun call-meth (obj1 obj2 message &rest args)
  (apply (field-value obj1 message) obj2 args))

(defun send (obj message &rest args)
  (apply #'call-meth obj obj message args))

(defmacro define-method (obj message lambda-list &body body)
  `(set-field-value ,obj ,message
                    (lambda (self ,@lambda-list)
                      (labels ((call-super (&rest args)
                                 (apply #'call-meth
                                        (super-object ,obj)
                                        self
                                        ,message
                                        args)))
                        ,@body))))


(define-method *circle* :clone ()
  (let ((result (call-super)))
    (set-field-value result :center
                     (send (send self :get-center) :clone))
    result))





; priklad - zpatky ke kruhu

(setf *shape* (send *object* :clone))

(define-method *shape* :get-color ()
  (field-value self :color))

(define-method *shape* :set-color (value)
  (set-field-value self :color value))

(send *shape* :set-color :black)


(setf *point* (send *shape* :clone))

(define-method *point* :get-x ()
  (field-value self :x))

(define-method *point* :get-y ()
  (field-value self :y))

(define-method *point* :set-x (value)
  (set-field-value self :x value))

(define-method *point* :set-y (value)
  (set-field-value self :y value))

(send *point* :set-x 0)
(send *point* :set-y 0)

(define-method *point* :move (dx dy)
  (send self :set-x (+ (send self :get-x) dx))
  (send self :set-y (+ (send self :get-y) dy))
  self)

(setf *circle* (send *shape* :clone))

(set-field-value *circle* :center (send *point* :clone))
(set-field-value *circle* :radius 0)

(define-method *circle* :get-center ()
  (field-value self :center))

(define-method *circle* :get-radius ()
  (field-value self :radius))

(define-method *circle* :set-radius (value)
  (set-field-value self :radius value))

(define-method *circle* :move (dx dy)
  (send (send self :get-center) :move dx dy)
  self)

(define-method *circle* :clone ()
  (let ((result (call-super)))
    (set-field-value result :center
                     (send (send self :get-center) :clone))
    result))

(setf circ1 (send *circle* :clone))
(setf circ2 (send *circle* :clone))

(send (send circ1 :get-center) :get-x)
(send (send circ2 :get-center) :get-x)

(send circ1 :move 20 20)

(send (send circ1 :get-center) :get-x)
(send (send circ2 :get-center) :get-x)

