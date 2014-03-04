;; class DERIV

(defclass deriv (binary-expression)
  ())

(defmethod set-expr-2 ((e deriv) expr)
  (unless (typep expr 'var)
    (error "Expr is not a variable. "))
  (setf (slot-value e 'expr-2) expr)
  e)

(defmethod bin-expr-symbol ((e deriv))
  'd)

(defvar *bin-expr-class* 'binary-expression)
(defvar *deriv-class* 'deriv)

(defmethod simplify ((e deriv))
  (simplify 
   (cond ((typep (expr-1 e) *const-expr-class*) (make-instance *const-expr-class*))
         ((typep (expr-1 e) *var-expr-class*)   (parse (if (eql (name v) (name var)) 1 0)))
         ((typep (expr-1 e) *bin-expr-class*)
           (let ((expr-1 (expr-1 (expr-1 e)))
                 (expr-2 (expr-2 (expr-1 e)))
                 (var (expr-2 e)))
             (cond ((or (typep (expr-1 e) *+-expr-class*) (typep (expr-1 e) *--expr-class*))
                    (parse `(,(bin-expr-symbol (expr-1 e)) ,(deriv expr-1 var) ,(deriv expr-2 var))))
                   ((typep (expr-1 e) **-expr-class*)
                    (parse `(+ (* ,(deriv expr-1 var) ,expr-2)
                               (* ,expr-1 ,(deriv expr-2 var)))))
                   ((typep (expr-1 e) */-expr-class*)
                    (parse `(/ (- (* ,(deriv expr-1 var) ,expr-2)
                                  (* ,expr-1 ,(deriv expr-2 var)))
                               (* ,expr-2 ,expr-2))))
                   (t e)))))))

;; PARSE function etc.

(defun make-binary-expr (name expr-1 expr-2)
  (let ((result (make-instance
                 (cond
                  ((eql name '+) *+-expr-class*)
                  ((eql name '-) *--expr-class*)
                  ((eql name '*) **-expr-class*)
                  ((eql name '/) */-expr-class*)
                  ((eql name 'd) *deriv-class*)))))
    (set-expr-1 result (parse expr-1))
    (set-expr-2 result (parse expr-2))
    result))

;; TESTs

(representation (simplify (parse '(d 5 x))))
(representation (simplify (parse '(d (* 2 y) x))))
(representation (simplify (parse '(d (* x x) x))))
(representation (simplify (parse '(d (/ x y) x))))
(representation (simplify (parse '(d (+ (- (* 3 (* x x)) (* y x)) 5) x))))
