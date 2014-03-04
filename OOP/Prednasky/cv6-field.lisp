;;; class field

(defclass field (picture)
  ((state :initform 'empty)))

(defmethod state ((field field))
  (slot-value field 'state))

(defmethod set-state ((field field) value)
  (if (or (equalp value 'empty)
          (equalp value 'cross)
          (equalp value 'circle))
      (setf (slot-value field 'state) value)
    (error "The value should be 'empty, 'cross or 'circle."))
  field)

(defmethod initialize-instance ((field field) &key)
  (call-next-method)
  (let ((border (make-instance 'polygon))
        (cross  (make-instance 'picture))
        (circle (make-instance 'circle)))

    ; set border
    (set-items border 
               (list (set-x (set-y (make-instance 'point)
                                   0)
                            0)
                     (set-x (set-y (make-instance 'point)
                                   0)
                            100)
                     (set-x (set-y (make-instance 'point)
                                   100)
                            100)
                     (set-x (set-y (make-instance 'point)
                                   100)
                            0)))
    (set-closedp border t)
    (set-thickness border 5)

    ; set cross
    (set-items cross
               (list (set-thickness
                      (set-items (make-instance 'polygon)
                                (list (set-x (set-y (make-instance 'point)
                                                    20)
                                             20)
                                      (set-x (set-y (make-instance 'point)
                                                    80)
                                             80)))
                      5)
                     (set-thickness 
                      (set-items (make-instance 'polygon)
                                (list (set-x (set-y (make-instance 'point)
                                                    20)
                                             80)
                                      (set-x (set-y (make-instance 'point)
                                                    80)
                                             20)))
                      5)))

    ; set circle
    (set-x (set-y (center circle)
                  50)
           50)
    (set-radius circle 30)
    (set-thickness circle 5)

    ; set items of a whole field
    (set-items field (list border cross circle)))
    field
 )

(defmethod do-draw ((field field))
  (draw (car (items field)))
  (cond ((equalp (state field) 'cross) (draw (car (cdr (items field)))))
        ((equalp (state field) 'circle) (draw (car (cdr (cdr (items field)))))))
  field)

; testing create-board

(defun create-board (rows cols)
  (let ((items '())
        (f nil))
    (dotimes (x rows)
      (dotimes (y cols)
        (setf f (make-instance 'field))
        (cond ((= (mod (+ (* x rows) y) 3) 1) (set-state f 'cross))
              ((= (mod (+ (* x rows) y) 3) 2) (set-state f 'circle)))
        (move f (* 100 y) (* 100 x))
        (setf items (cons f items))))
    (set-items (make-instance 'picture) items)))
                     
