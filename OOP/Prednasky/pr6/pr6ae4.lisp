;; class EXPRESSION

(defclass expression ()
  ())

(defmethod deriv ((expr expression) var)
  (error "Method deriv has to be rewritten. "))

(defmethod expr-subst ((expr expression) var substituent)
  (error "Method expr-subst has to be rewritten. "))

(defmethod representation ((expr expression))
  expr)

(defmethod simplify ((expr expression))
  expr)



;; class CONST

(defclass const (expression)
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

(defmethod representation ((c const))
  (slot-value c 'value))



;; class VAR

(defclass var (expression)
  ((name :initform 'x)))

(defmethod name ((v var))
  (slot-value v 'name))

(defmethod set-name ((v var) name)
  (unless (symbolp name)
    (error "Name should be a symbol. "))
  (setf (slot-value v 'name) name)
  v)

(defmethod deriv ((v var) var)
  (parse
   (if (eql (name v)
            (name var))
       1 0)))

(defmethod expr-subst ((v var) var substituent)
  (if (eql (name v)
           (name var))
      substituent
    v))

(defmethod representation ((expr var))
  (slot-value expr 'name))



;; class BINARY-EXPRESSION

(defclass binary-expression (expression)
  ((expr-1 :initform (make-instance 'const))
   (expr-2 :initform (make-instance 'const))))

(defmethod expr-1 ((e binary-expression))
   (slot-value e 'expr-1))

(defmethod expr-2 ((e binary-expression))
   (slot-value e 'expr-2))

(defmethod set-expr-1 ((e binary-expression) expr)
  (unless (typep expr 'expression)
    (error "Expr is not an expression. "))
  (setf (slot-value e 'expr-1) expr)
  e)

(defmethod set-expr-2 ((e binary-expression) expr)
  (unless (typep expr 'expression)
    (error "Expr is not an expression. "))
  (setf (slot-value e 'expr-2) expr)
  e)

(defmethod expr-subst ((e binary-expression)  var substituent)
  (let ((result (make-instance (type-of e))))
    (set-expr-1 result (expr-subst (expr-1 e) var substituent))
    (set-expr-2 result (expr-subst (expr-2 e) var substituent))
    result))

(defmethod representation ((e binary-expression)) 
  `(,(bin-expr-symbol e)
    ,(representation (expr-1 e))   
    ,(representation (expr-2 e))))

(defmethod bin-expr-symbol ((expr binary-expression))
  (error "Method bin-expr-symbol has to be rewritten. "))



;; class +-EXPR

(defclass +-expr (binary-expression)
  ())

(defmethod bin-expr-symbol ((expr +-expr))
  '+)

(defmethod deriv ((expr +-expr) var)
  (let ((expr-1 (expr-1 expr))
        (expr-2 (expr-2 expr)))
    (parse `(+ ,(deriv expr-1 var) ,(deriv expr-2 var)))))


;; class --EXPR

(defclass --expr (binary-expression)
  ())

(defmethod bin-expr-symbol ((expr --expr))
  '-)

(defmethod deriv ((expr --expr) var)
  (let ((expr-1 (expr-1 expr))
        (expr-2 (expr-2 expr)))
    (parse `(- ,(deriv expr-1 var) ,(deriv expr-2 var)))))


;; class *-EXPR

(defclass *-expr (binary-expression)
  ())

(defmethod bin-expr-symbol ((expr *-expr))
  '*)

(defmethod deriv ((expr *-expr) var)
  (let ((expr-1 (expr-1 expr))
        (expr-2 (expr-2 expr)))
    (parse `(+ (* ,(deriv expr-1 var) ,expr-2)
               (* ,expr-1 ,(deriv expr-2 var))))))


;; class /-EXPR

(defclass /-expr (binary-expression)
  ())

(defmethod bin-expr-symbol ((expr /-expr))
  '/)

(defmethod deriv ((expr /-expr) var)
  (let ((expr-1 (expr-1 expr))
        (expr-2 (expr-2 expr)))
    (parse `(/ (- (* ,(deriv expr-1 var) ,expr-2)
                  (* ,expr-1 ,(deriv expr-2 var)))
               (* expr-2 expr-2)))))



;; PARSE function

(defvar *const-expr-class* 'const)
(defvar *var-expr-class* 'var)
(defvar *+-expr-class* '+-expr)
(defvar *--expr-class* '--expr)
(defvar **-expr-class* '*-expr)
(defvar */-expr-class* '/-expr)

(defun make-binary-expr (name expr-1 expr-2)
  (let ((result (make-instance
                 (cond
                  ((eql name '+) *+-expr-class*)
                  ((eql name '-) *--expr-class*)
                  ((eql name '*) **-expr-class*)
                  ((eql name '/) */-expr-class*)))))
    (set-expr-1 result (parse expr-1))
    (set-expr-2 result (parse expr-2))
    result))

(defun make-const (value)
  (let ((result (make-instance *const-expr-class*)))
    (set-value result value)
    result))

(defun make-var (name)
  (let ((result (make-instance *var-expr-class*)))
    (set-name result name)
    result))

(defun parse (repr)  
  (cond
    ((typep repr 'number) (make-const repr))
    ((typep repr 'symbol) (make-var repr))  
    ((typep repr 'list) (apply 'make-binary-expr repr))
    ((typep repr 'expression) repr)
    (t (error "Cannot parse ~s. " repr))))


;; SIMPLIFY

(defmethod simplify ((expr binary-expression))
  (parse `(,(bin-expr-symbol expr)
	   ,(simplify (expr-1 expr))
	   ,(simplify (expr-2 expr)))))

(defmethod zero-const-p ((expr expression))
  nil)

(defmethod zero-const-p ((expr const))
  (zerop (value expr)))

(defmethod simplify ((expr +-expr))
  (let* ((result (call-next-method))
	 (expr-1 (expr-1 result))
	 (expr-2 (expr-2 result)))
    (cond ((zero-const-p expr-1) expr-2)
	  ((zero-const-p expr-2) expr-1)
	  (t result))))

(defmethod simplify ((expr --expr))
  (let* ((result (call-next-method))
	 (expr-1 (expr-1 result))
	 (expr-2 (expr-2 result)))
    (if (zero-const-p expr-2) expr-1
	result)))



(defmethod one-const-p ((expr expression))
  nil)

(defmethod one-const-p ((expr const))
  (= (value expr) 1))


(defmethod simplify ((expr *-expr))
  (let* ((result (call-next-method))
	 (expr-1 (expr-1 result))
	 (expr-2 (expr-2 result)))
    (cond ((or (zero-const-p expr-1) (zero-const-p expr-2)) (make-instance 'const))
          ((one-const-p expr-1) expr-2)
          ((one-const-p expr-2) expr-1)
	  (t result))))

(defmethod simplify ((expr /-expr))
  (let* ((result (call-next-method))
	 (expr-1 (expr-1 result))
	 (expr-2 (expr-2 result)))
    (if (one-const-p expr-2) expr-1
	result)))


;; TESTs

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
(representation (simplify (deriv *mul* *x*)))