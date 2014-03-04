;(defclass queue ()
;  ((start :initform (cons nil nil))
;   (end :initform (slot-value ??? 'start))))

(defclass queue ()
  (start end))

(defmethod initialize-instance ((q queue) &key)
  (let ((empty-q (cons nil nil)))
    (setf (slot-value q 'start) empty-q)
    (setf (slot-value q 'end) empty-q)))

(defmethod in ((q queue) item)
  (let ((new-item (cons item nil)))
    (setf (cdr (slot-value q 'end)) new-item)
    (setf (slot-value q 'end) new-item))
  q)

(defmethod out ((q queue))
  (let ((out (cadr (slot-value q 'start))))
    (if (eql (slot-value q 'start) (slot-value q 'end))
        (setf (cdr (slot-value q 'start)) nil)
      (setf (slot-value q 'start) (cdr (slot-value q 'start))))
    out))

;; TESTs

(defvar *q*)
(setf *q* (make-instance 'queue))
(in *q* 1)
(in *q* 2)
(out *q*)