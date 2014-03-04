; (letrec ((s1 v1) .. (sN vN)) <tělo>)  =  (let () (define s1 v1) .. (define sN vN) <tělo>)

(define-macro letrec (lambda (param . body)
                       `(let ()
                          ,@(map (lambda (cell) `(define ,(car cell) ,(cadr cell))) 
                                 param)
                          ,@body)))

(letrec ((s1 10) (s2 20)) (+ s1 s2))   ; 30
(letrec ((f (lambda (x) (if (= x 0) 1 (* x (f (- x 1))))))) (f 5))   ; 120


(define-macro procedure (lambda (args . body)
                          `(lambda ,args
                             ((lambda (y) (y y ,@args))
                              (lambda (self ,@args)
                                (let ((self (lambda ,args (self self ,@args))))
                                  ,@body))))))
(define-macro self (lambda args
                     `(self* self* ,@args)))

((procedure (n) (if (= n 1) 1 (* n (self (- n 1))))) 5)   ; 120
(let ((f (procedure (x) (if (list? x) (apply + (map self x)) 1))))
  (f '(a ((b c) ((d))) ((e) f))))   ; 6