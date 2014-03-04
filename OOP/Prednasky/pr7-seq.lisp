
;; class MY-SEQUENCE

(defclass my-sequence ()
  ())

; physical level

(defmethod after-end-p ((sequence my-sequence) position)
  (error "Method after-end-p has to be rewritten."))

(defmethod before-start-p ((sequence my-sequence) position)
  (error "Method before-start-p has to be rewritten."))

(defmethod first-position ((sequence my-sequence))
  (error "Method first-position has to be rewritten."))

(defmethod last-position ((sequence my-sequence))
  (error "Method last-position has to be rewritten."))

(defmethod next-position ((sequence my-sequence) position)
  (error "Method next-position has to be rewritten."))

(defmethod prev-position ((sequence my-sequence) position)
  (error "Method prev-position has to be rewritten."))

(defmethod position-item ((seq my-sequence) pos)
  (error "Method position-item has to be rewritten."))

(defmethod set-position-item ((seq my-sequence) pos item)
  (error "Method set-position-item has to be rewritten."))

; logical level

(defmethod nth-pos ((seq my-sequence) index)
  (labels ((iter (position i)
	     (if (= i 0)
		 position
	       (iter (next-position seq position) 
		     (- i 1)))))
    (iter (first-position seq) index)))

(defmethod my-elt ((seq my-sequence) index)
  (position-item seq (nth-pos seq index)))

(defmethod my-set-elt ((seq my-sequence) index value)
  (set-position-item seq (nth-pos seq index) value))

(defmethod my-length (seq)
  (labels ((iter (index pos)
	     (if (after-end-p seq pos)
		 index
	       (iter (+ index 1)
		     (next-position seq pos)))))
    (iter 0 (first-position seq))))

(defmethod my-find ((seq my-sequence) elem)
  (labels ((iter (pos)
	     (unless (after-end-p seq pos)
	       (if (eql elem (position-item seq pos))
		   elem
		 (iter (next-position seq pos))))))
    (iter (first-position seq))))

;; introduction to ARRAYs

(defvar *a*)
(setf *a* (make-array 5))
(setf (aref *a* 3) 5)
(aref *a* 2)
(aref *a* 3)
;(aref *a* 10)

(defvar *s*)
(setf *s* "Ahoj svete")

(defvar *t*)
(setf *t* (copy-seq *s*))
(setf (aref *t* 7) #\i)



;; class MY-VECTOR

(defclass my-vector (my-sequence)
  ((representation :initform "")))

; get/set slot

(defmethod representation ((vector my-vector))
  (copy-seq (slot-value vector 'representation)))

(defmethod set-representation ((vector my-vector) value)
  (setf (slot-value vector 'representation) 
	(copy-seq value))
  vector)

; physical level

(defmethod after-end-p ((vec my-vector) position)
 (>= position (my-length vec))) 

(defmethod before-start-p ((vec my-vector) position)
  (<= position 0))

(defmethod first-position ((vec my-vector))
  0)

(defmethod last-position ((vec my-vector))
  (- (my-length vec) 1))

(defmethod next-position ((vec my-vector) position)
  (+ position 1))

(defmethod prev-position ((vec my-vector) position)
  (- position 1))

(defmethod position-item ((vec my-vector) position)
  (aref (slot-value vec 'representation)
	position))

(defmethod set-position-item ((vec my-vector) position value)
  (setf (aref (slot-value vec 'representation) 
	      position) 
	value)
  vec)

; logical level

(defmethod my-length ((vec my-vector))
  (length (representation vec)))


;; TESTs

(defvar *vec*)
(setf *vec* (make-instance 'my-vector))
(set-representation *vec* "ABCDEFGHIJKLMN")
(representation *vec*)

(dotimes (i 10)
  (print (my-elt *vec* i)))

(my-find *vec* #\C)
(my-find *vec* #\a)

(progn
  (my-set-elt *vec* 8 #\c)
  (my-set-elt *vec* 9 #\h)
  (my-set-elt *vec* 10 #\i)
  (my-set-elt *vec* 11 #\j)
  (my-set-elt *vec* 12 #\k)
  (my-set-elt *vec* 13 #\l))
(representation *vec*)



;; class MY-LIST

(defclass my-list (my-sequence)
  ())

; my-list common methods 

(defmethod my-list-to-list ((list my-list))
  (error "Method my-list-to-list has to be rewritten."))

(defmethod my-empty-list-p ((list my-list))
  (error "Method my-empty-list-p has to be rewritten."))

; physical level

(defmethod after-end-p ((list my-list) position)
  (my-empty-list-p position))

(defmethod before-start-p ((list my-list) position)
  (my-empty-list-p position))

(defmethod first-position ((list my-list))
  list)

;; class MY-EMPTY-LIST

(defclass my-empty-list (my-list)
  ())

; my-list common methods

(defmethod my-list-to-list ((list my-empty-list))
  '())

(defmethod my-empty-list-p ((list my-empty-list))
  t)

; physical level

(defmethod last-position ((list my-empty-list))
  list)

(defmethod next-position ((list my-empty-list) position)
  (error "Method next-position can not be used with an empty list."))

(defmethod prev-position ((list my-empty-list) position)
  (error "Method prev-position can not be used with an empty list."))

(defmethod position-item ((list my-empty-list) pos)
  (error "Method position-item can not be used with an empty list."))

(defmethod set-position-item ((list my-empty-list)pos item)
  (error "Method set-position-item can not be used with an empty list."))

;; class MY-CONS

(defclass my-cons (my-list)
  ((my-car :initform nil)
   (my-cdr :initform (make-instance 'my-empty-list))))

; set/get slots

(defmethod my-car ((cons my-cons))
  (slot-value cons 'my-car))

(defmethod my-cdr ((cons my-cons))
  (slot-value cons 'my-cdr))

(defmethod set-my-car ((cons my-cons) value)
  (setf (slot-value cons 'my-car) value))

(defmethod set-my-cdr ((cons my-cons) value)
  (setf (slot-value cons 'my-cdr) value))

; my-list common methods

(defmethod my-list-to-list ((list my-cons))
  (cons (my-car list)
	(my-list-to-list (my-cdr list))))

(defmethod my-empty-list-p ((list my-cons))
  nil)

; physical level

(defmethod next-position ((list my-cons) position)
  (my-cdr position))

(defmethod prev-position ((list my-cons) position)
  (cond ((my-empty-list-p position)
	   (error "There is no previous position"))
	((eql position (my-cdr list))
	   list)
	(t (prev-position (my-cdr list) position))))

(defmethod position-item ((list my-cons) position)
  (my-car position))

(defmethod set-position-item ((list my-cons) position value)
  (setf (slot-value position 'my-car) value)
  list)

(defmethod last-position ((list my-cons))
  (if (my-empty-list-p (my-cdr list))
      list
    (last-position (my-cdr list))))


; auxiliary function

(defun list-to-my-list (list)
  (if (null list)
      (make-instance 'my-empty-list)
    (let ((new (make-instance 'my-cons)))
      (set-my-car new (car list))
      (set-my-cdr new 
                  (list-to-my-list (cdr list)))
      new)))

;; TESTs

(defvar *list*)
(setf *list* (list-to-my-list '(1 2 3 4 5 6 7)))
(my-list-to-list *list*)

(dotimes (i 7)
  (print (my-list-to-list (nth-pos *list* i))))

(my-length *list*)

(my-find *list* 5)
(my-find *list* 10)

(my-elt *list* 5)
;(my-elt *list* 20)

