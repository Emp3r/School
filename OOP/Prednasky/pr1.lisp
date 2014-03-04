(defvar a 3)

(defun a (a b)
  (* a b))

(defun encap-car (list)
  (list (car list)))

(defun plus3 (x)
  (+ 3 x))
(defun comp (f g)
  (lambda (x)
    (f (g x))))

(defun comp2 (f g)
  (lambda (x)
    (funcall f (funcall g x))))


(defvar x 2)
(if (>= x 3) (+ x 2) (- x 3))

(+ x (if 3 4 neznamy))

(funcall (if (< x 10) #'+ #'-) 10)

(cond ((< x 0) (+ 1 2) (- 5))
      ((= x 1) 5)
      (t (+ 10 x)))

(dotimes (i 10)
  (print (* 2 i)))

(dolist (i '(1 2 3 10 15))
  (print i))


(defvar xx 5)

(when (= xx 5)
  (print 1) (print 2) (print 10))

(unless (= xx 5)
  (print 1) (print 2) (print 10))


(let ((xx 1) (yy 2))
  (+ 1 xx yy))

(let* ((a 1) (b (* 2 a)))
  (+ 1 a b))

;listener
(defvar *a* 5)
*a*
(defvar *a* 6)
*a*
(defvar *a*)
(setf *a* 6)

(setf *a* (cons 1 2))
*a*
(setf (car *a*) 3)
*a*
(setf (car *a*) 5 (cdr *a*) 7)
*a*


(defun pokus (x y z)
  (+ x y z))

(lambda (x y z) (+ x y z))
((lambda (x y z) (+ x y z)) 1 2 3)

(defun rek (n)
  (labels ((r1 (n o)
             (r2 (- n 1) (* o 2)))
           (r2 (n o)
             (if (> 0 n) o (r1 (- n 2) (+ o 1)))))
    (r1 n 1)))

(apply (function +) 1 2 3 '(4 5 6))
(funcall (function +) 1 2 3 4 5 6)
(+ 1 2 3 4 5 6)


(not (+ 3 1))
(not (- 3 3))
(or t nil neznamy)
(and t nil neznamy)


(eql 'abc 'abc)
(eql "ahoj" "ahoj")
(eql 1 1)
(eql 1 1.0)
(eql '(1 2) '(1 2))

(equalp 1 1.0)
(equalp '(1 (2 3)) '(1 (2 3)))
(equalp (lambda (x) x) (lambda (x) (x)))
(equalp "ahoj" "AHOJ")

(= 1 1.0)
(/= 1  9/9)
(< 0.9999999999999  1)
(< 99999999999999/100000000000000 1)
(>= 12 12)

(+ (/ 2) (- -3) (*) (+))
(- 10 5 2 3)
(min -3 5 1 (abs -5))
(expt 2 4)
(log (exp 5))
(+ 3 (floor pi))
(ceiling pi)
(round 2.77)
(mod 22 3)
(rem 22 3)


(cons (cons 1 2) 3) 
(car '(1 2 3))
(cdr '(1 2 3))
(car nil)
(cadddr '(1 2 3 4 5 6 7))
(fourth '(1 2 3 4 5 6 7))
(null '())
(null nil)
(reverse '(1 2 (3 4) 5 6 7))
(mapcar (lambda (x) (+ x 3)) '(1 2 3 4 5))
(mapcar #'- '(1 2 3))
(mapcar #'cons '(1 2 3 4) '(a b cc))
(find 2 '(1 2 3))
(find 2 '(1 (2 3)))
(find '(2 3) '(1 (2 3)))
(find-if #'evenp '(1 -1 3 5 4 2 1 3))
(remove 2 '(1 (2 3) 2 3 4 2 1))
(remove-if #'oddp '(1 2 3 4 5 4 3 2 1))
(length '(1 2 3 4 5))

(defun and-fun (&rest args)
  ;(print args)
  (if (null args) t
    (if (car args) (apply #'and-fun (cdr args))
      nil)))
(and-fun 1 2)
(apply #'and-fun (mapcar #'evenp '(2 4 6 4)))
(every #'evenp '(2 4 6))


(typep (+ 3 2.5) 'number)
(typep (+ 3.5 2.5) 'integer)
(typep (lambda (x) x) 'function)
(typep '(1 2 3) 'cons)
(typep (cons 1 2) 'list)
(typep '() 'cons)
(typep '() 'list)
(typep '() 'null)
(typep '() 'nil)

(defun plus (a b)
  (if (and (typep a 'number) (typep b 'number))
      (+ a b)
    (error "Arguments must be numbers.")))
(plus 10 20)
(plus 'a 1)

(defun test (x y) 
  (if (> x 20) y
  (test (+ x 1) (+ y x))))

(print (list 1 2 3 (cons 1 2)))

(format t "Tady je nejaky text ~s ~%a nejaky symbol ~s, cislo ~s a seznam ~s. "
        "Ahoj svete!" 'ahoj 12.52 (cons 1 (cons 2 3)))
(format nil "Tady je nejaky text ~a ~%a nejaky symbol ~a, cislo ~a a seznam ~a. "
        "Ahoj svete!" 'ahoj 12.52 (cons 1 (cons 2 3)))
