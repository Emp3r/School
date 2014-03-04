
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
*a*

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
      (set-my-cdr new (list-to-my-list (cdr list)))
      new)))

;; TESTs

(defvar *list*)
(setf *list* (list-to-my-list '(1 2 3 4 5 6 7)))
(my-list-to-list *list*)

(position-item *list* (nth-pos *list* 0))
(dotimes (i 7)
  (print (my-list-to-list (nth-pos *list* i))))

(my-length *list*)

(my-find *list* 5)
(my-find *list* 10)

(my-elt *list* 5)
;(my-elt *list* 20)

;--------------------------------------------------------------------------------------------------------------------
;--------------------------------------------------------------------------------------------------------------------
;--------------------------------------------------------------------------------------------------------------------

;; my-dlist
(defclass my-dlist (my-sequence)
  ())


(defmethod my-dlist-to-list ((dlist my-dlist))
  (error "Method my-list-to-list has to be rewritten."))

(defmethod my-empty-dlist-p ((dlist my-dlist))
  (error "Method my-empty-list-p has to be rewritten."))


; physical level
(defmethod after-end-p ((dlist my-dlist) position)
  (my-empty-dlist-p position))

(defmethod before-start-p ((dlist my-dlist) position)
  (my-empty-dlist-p position))

(defmethod first-position ((dlist my-dlist))
  dlist)


;; my-empty-dlist
(defclass my-empty-dlist (my-dlist)
  ())

(defmethod my-dlist-to-list ((dlist my-empty-dlist))
  '())
(defmethod my-empty-dlist-p ((dlist my-empty-dlist))
  t)

; physical level
(defmethod last-position ((dlist my-empty-dlist))
  nil)

(defmethod next-position ((dlist my-empty-dlist) position)
  (error "Method next-position can not be used with an empty dlist."))

(defmethod prev-position ((dlist my-empty-dlist) position)
  (error "Method prev-position can not be used with an empty dlist."))

(defmethod position-item ((dlist my-empty-dlist) pos)
  (error "Method position-item can not be used with an empty dlist."))

(defmethod set-position-item ((dlist my-empty-dlist)pos item)
  (error "Method set-position-item can not be used with an empty dlist."))


;; my-dcons
(defclass my-dcons (my-dlist)
  ((value :initform nil)
   (prev :initform (make-instance 'my-empty-dlist))
   (next :initform (make-instance 'my-empty-dlist))))

; get/set 
(defmethod get-value ((dl my-dlist))
  (slot-value dl 'value))
(defmethod set-value ((dl my-dlist) value)
  (setf (slot-value dl 'value) value)
  dl)
(defmethod get-prev ((dl my-dlist))
  (slot-value dl 'prev))
(defmethod set-prev ((dl my-dlist) value)
  (setf (slot-value dl 'prev) value)
  dl)
(defmethod get-next ((dl my-dlist))
  (slot-value dl 'next))
(defmethod set-next ((dl my-dlist) value)
  (setf (slot-value dl 'next) value)
  dl)


(defmethod my-dlist-to-list ((dlist my-dcons))
  (cons (get-value dlist)
        (if (not (null (get-next dlist)))
            (my-dlist-to-list (get-next dlist))
          '())))

(defmethod my-empty-dlist-p ((dlist my-dcons))
  nil)


; physical level
(defmethod next-position ((dlist my-dcons) position)
  (get-next position))

(defmethod last-position ((dlist my-dcons))
  (if (my-empty-dlist-p (get-next dlist))
      dlist
    (last-position (get-next dlist))))

(defmethod prev-position ((dlist my-dcons) position)
  (cond ((my-empty-dlist-p position)
	   (error "There is no previous position"))
	( t (get-prev position))))

(defmethod position-item ((dlist my-dcons) position)
  (get-value position))

(defmethod set-position-item ((dlist my-dcons) position value)
  (setf (slot-value position 'value) value)
  dlist)


(defun list-to-my-dlist (list)
  (if (null list)
      (make-instance 'my-empty-dlist)
    (let ((new-dlist (make-instance 'my-dcons)))
      (set-value new-dlist (car list))
      (labels ((iter (l previous)
                 (if (null l)
                     (make-instance 'my-empty-dlist)
                   (let ((t-dlist (make-instance 'my-dcons)))
                     (if (my-empty-dlist-p (get-next new-dlist))
                         (set-next new-dlist t-dlist))
                     (set-value t-dlist (car l))
                     (set-prev t-dlist previous)
                     (set-next t-dlist (iter (cdr l) t-dlist))))))
        (iter (cdr list) new-dlist)
        new-dlist))))


; TESTS ;
(defvar *dlist*)
(setf *dlist* (list-to-my-dlist '(1 2 3 4 5 6 7 8)))

(position-item *dlist* (nth-pos *dlist* 4))

(my-elt *dlist* 3)
(my-elt *dlist* 4)

(my-length *dlist*)

(my-dlist-to-list *dlist*)

(my-find *dlist* 5)
(my-find *dlist* 8)

(my-dlist-to-list (last-position *dlist*))

(dotimes (i 5)
  (print (my-dlist-to-list (nth-pos *dlist* i))))

