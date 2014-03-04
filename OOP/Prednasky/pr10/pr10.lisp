
(defclass A ()
  ((i :initform 1)))

(defclass B (A1)
  ())

(defclass C (A2)
  ())

(defclass D (B C)
  ())

(defmethod test ((o A))
  (format t "A"))

(defmethod test ((o A1))
  (format t "A1")
  (call-next-method))

(defmethod test ((o A2))
  (format t "A2"))

(defmethod test ((o B))
  (format t "B")
  (call-next-method))

(defmethod test ((o C))
  (format t "C")
  (call-next-method))

(defmethod test ((o D))
  (format t "D")
  (call-next-method))

(defvar pok)
(setf pok (make-instance 'D))

(slot-value pok 'i)
(setf (slot-value pok 'i) 5)

(test pok)

(defclass A2 ()
  ((i :initform 2)))

(defclass A1 ()
  ((i :initform 1)))



(defclass E (A1 A2)
  ())


(setf pok (make-instance 'E))
