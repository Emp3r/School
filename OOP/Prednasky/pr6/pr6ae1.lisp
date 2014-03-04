;; class CONST

(defclass const ()
  ((value :initform 0)))

(defmethod value ((c const))
  (slot-value c 'value))

(defmethod set-value ((c const) value)
  (unless (numberp value)
    (error "Value should be a number. "))
  (setf (slot-value c 'value) value)
  c)

(defmethod deriv ((c const) var)
  (make-instance 'const))

(defmethod expr-subst ((c const) var substituent)
  c)



;; class VAR

(defclass var ()
  ((name :initform 'x)))

(defmethod name ((v var))
  (slot-value v 'name))

(defmethod set-name ((v var) name)
  (unless (symbolp name)
    (error "Name should be a symbol. "))
  (setf (slot-value v 'name) name)
  v)

(defmethod deriv ((v var) var)
  (let ((result (make-instance 'const)))
    (set-value result
          (if (eql (name v)
                   (name var))
              1 0))
    result))

(defmethod expr-subst ((v var) var substituent)
  (if (eql (name v)
           (name var))
      substituent
    v))



;: class +-EXPR

(defclass +-expr ()
  ((expr-1 :initform (make-instance 'const))
   (expr-2 :initform (make-instance 'const))))

(defmethod expr-1 ((e +-expr))
   (slot-value e 'expr-1))

(defmethod expr-2 ((e +-expr))
   (slot-value e 'expr-2))

(defmethod set-expr-1 ((e +-expr) expr)
  (unless (or (typep expr 'const)
              (typep expr 'var)
              (typep expr '+-expr)
              (typep expr '--expr)
              (typep expr '*-expr)
              (typep expr '/-expr))
    (error "Expr is not an expression. "))
  (setf (slot-value e 'expr-1) expr)
  e)

(defmethod set-expr-2 ((e +-expr) expr)
  (unless (or (typep expr 'const)
              (typep expr 'var)
              (typep expr '+-expr)
              (typep expr '--expr)
              (typep expr '*-expr)
              (typep expr '/-expr))
    (error "Expr is not an expression. "))
  (setf (slot-value e 'expr-2) expr)
  e)
              
(defmethod deriv ((expr +-expr) var)
  (let ((result (make-instance '+-expr)))
    (set-expr-1 result (deriv (expr-1 expr) var))
    (set-expr-2 result (deriv (expr-2 expr) var))
    result))

(defmethod expr-subst ((expr +-expr) var substituent)
  (let ((result (make-instance '+-expr)))
    (set-expr-1 result (expr-subst (expr-1 expr) var substituent))
    (set-expr-2 result (expr-subst (expr-2 expr) var substituent))
    result))



;; class --EXPR

(defclass --expr ()
  ((expr-1 :initform (make-instance 'const))
   (expr-2 :initform (make-instance 'const))))

(defmethod expr-1 ((e --expr))
   (slot-value e 'expr-1))

(defmethod expr-2 ((e --expr))
   (slot-value e 'expr-2))

(defmethod set-expr-1 ((e --expr) expr)
  (unless (or (typep expr 'const)
              (typep expr 'var)
              (typep expr '+-expr)
              (typep expr '--expr)
              (typep expr '*-expr)
              (typep expr '/-expr))
    (error "Expr is not an expression. "))
  (setf (slot-value e 'expr-1) expr)
  e)

(defmethod set-expr-2 ((e --expr) expr)
  (unless (or (typep expr 'const)
              (typep expr 'var)
              (typep expr '+-expr)
              (typep expr '--expr)
              (typep expr '*-expr)
              (typep expr '/-expr))
    (error "Expr is not an expression. "))
  (setf (slot-value e 'expr-2) expr)
  e)
              
(defmethod deriv ((expr --expr) var)
  (let ((result (make-instance '--expr)))
    (set-expr-1 result (deriv (expr-1 expr) var))
    (set-expr-2 result (deriv (expr-2 expr) var))
    result))

(defmethod expr-subst ((expr --expr) var substituent)
  (let ((result (make-instance '--expr)))
    (set-expr-1 result (expr-subst (expr-1 expr) var substituent))
    (set-expr-2 result (expr-subst (expr-2 expr) var substituent))
    result))



;; class *-EXPR

(defclass *-expr ()
  ((expr-1 :initform (make-instance 'const))
   (expr-2 :initform (make-instance 'const))))

(defmethod expr-1 ((e *-expr))
   (slot-value e 'expr-1))

(defmethod expr-2 ((e *-expr))
   (slot-value e 'expr-2))

(defmethod set-expr-1 ((e *-expr) expr)
  (unless (or (typep expr 'const)
              (typep expr 'var)
              (typep expr '+-expr)
              (typep expr '--expr)
              (typep expr '*-expr)
              (typep expr '/-expr))
    (error "Expr is not an expression. "))
  (setf (slot-value e 'expr-1) expr)
  e)

(defmethod set-expr-2 ((e *-expr) expr)
  (unless (or (typep expr 'const)
              (typep expr 'var)
              (typep expr '+-expr)
              (typep expr '--expr)
              (typep expr '*-expr)
              (typep expr '/-expr))
    (error "Expr is not an expression. "))
  (setf (slot-value e 'expr-2) expr)
  e)
              
(defmethod deriv ((expr *-expr) var)
  (let ((result (make-instance '+-expr))
        (first  (make-instance '*-expr))
        (second (make-instance '*-expr)))
    (set-expr-1 first (deriv (expr-1 expr) var))
    (set-expr-2 first (expr-2 expr))
    (set-expr-1 second (expr-1 expr))
    (set-expr-2 second (deriv (expr-2 expr) var))
    (set-expr-1 result first)
    (set-expr-2 result second)
    result))

(defmethod expr-subst ((expr *-expr) var substituent)
  (let ((result (make-instance '*-expr)))
    (set-expr-1 result (expr-subst (expr-1 expr) var substituent))
    (set-expr-2 result (expr-subst (expr-2 expr) var substituent))
    result))



;; class /-EXPR

(defclass /-expr ()
  ((expr-1 :initform (make-instance 'const))
   (expr-2 :initform (make-instance 'const))))

(defmethod expr-1 ((e /-expr))
   (slot-value e 'expr-1))

(defmethod expr-2 ((e /-expr))
   (slot-value e 'expr-2))

(defmethod set-expr-1 ((e /-expr) expr)
  (unless (or (typep expr 'const)
              (typep expr 'var)
              (typep expr '+-expr)
              (typep expr '--expr)
              (typep expr '*-expr)
              (typep expr '/-expr))
    (error "Expr is not an expression. "))
  (setf (slot-value e 'expr-1) expr)
  e)

(defmethod set-expr-2 ((e /-expr) expr)
  (unless (or (typep expr 'const)
              (typep expr 'var)
              (typep expr '+-expr)
              (typep expr '--expr)
              (typep expr '*-expr)
              (typep expr '/-expr))
    (error "Expr is not an expression. "))
  (setf (slot-value e 'expr-2) expr)
  e)
              
(defmethod deriv ((expr /-expr) var)
  (let ((result (make-instance '/-expr))
        (numerator (make-instance '--expr))
        (denominator (make-instance '*-expr))
        (first (make-instance '*-expr))
        (second (make-instance '*-expr)))
    (set-expr-1 first (deriv (expr-1 expr) var))
    (set-expr-2 first (expr-2 expr))
    (set-expr-1 second (expr-1 expr))
    (set-expr-2 second (deriv (expr-2 expr) var))
    (set-expr-1 numerator first)
    (set-expr-2 numerator second)
    (set-expr-1 denominator (expr-2 expr))
    (set-expr-2 denominator (expr-2 expr))
    (set-expr-1 result numerator)
    (set-expr-2 result denominator)
    result))

(defmethod expr-subst ((expr /-expr) var substituent)
  (let ((result (make-instance '/-expr)))
    (set-expr-1 result (expr-subst (expr-1 expr) var substituent))
    (set-expr-2 result (expr-subst (expr-2 expr) var substituent))
    result))

(defvar *c)
(setf *c* (set-value (make-instance 'const) 5))
(representation *c*)

(defvar *x*)
(setf *x* (make-instance 'var))
(representation *x*)

(defvar *y*)
(setf *y* (set-name (make-instance 'var) 'y))
(representation *y*)

(defvar *plus*)
(setf *plus* (make-instance '+-expr))
(set-expr-1 *plus* *x*)
(set-expr-2 *plus* *c*)
(representation *plus*)
(representation (expr-subst *plus* *x* (make-instance 'const)))
(representation (expr-subst *plus* *y* (make-instance 'const)))

(defvar *mul*)
(setf *mul* (make-instance '*-expr))
(set-expr-1 *mul* *plus*)
(set-expr-2 *mul* *y*)
(representation *mul*)
(representation (deriv *mul* *x*))


