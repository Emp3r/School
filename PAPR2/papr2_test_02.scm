;; prvni pisemka
;; 1
(call/cc(lambda (x)
          ((lambda (y)
             (* y (+ 3 y)))
           (x 5))))

(+ 10
   (call/cc (lambda (x)
              (* 2 (call/cc (lambda (y)
                              (- 2 (y (x 7)))))))))

;; 2
(define-syntax switch
  (syntax-rules (case default)
    ((switch value
             (case c1 e1 ...)
             ...)
     (let ((result value))
       (cond ((= value c1) (begin e1 ...))
             ...)))
    ((switch value
             (case c1 e1 ...)
             ...
             (default e ...))
     (let ((result value))
       (cond ((= value c1) (begin e1 ...))
             ...
             (else (begin e ...)))))))

(switch (+ 1 1)
        (case 1 (display "one") (newline))
        (case 2 (display "two") (newline))
        (case 3 (display "three") (newline)))

(switch (+ 1 5)
        (case 1 (display "one") (newline))
        (case 2 (display "two") (newline))
        (case 3 (display "three") (newline))
        (default (display "none") (newline)))

;; 3
(define-macro cons-stream
  (lambda (a b)
    `(cons ,a (delay ,b))))
(define stream-car car)
(define stream-cdr
  (lambda (stream)
    (force (cdr stream))))

(define pow-gen
  (lambda (n)
    (cons-stream (lambda (x)
                   (expt x n))
                 (pow-gen (+ n 1)))))

(define powers (pow-gen 0))

((stream-car powers) 2)
((stream-car (stream-cdr powers)) 2)
((stream-car (stream-cdr (stream-cdr powers))) 2)
((stream-car (stream-cdr (stream-cdr (stream-cdr powers)))) 2)

;; druha pisemka
;; 1
(+ 10
   (call/cc (lambda (x)
              (* 2 (call/cc (lambda (y)
                              (x (- 2 (y 7)))))))))

((lambda (x y)
   (+ x (call/cc (lambda (f)
                   (if (< x y) (f 5)
                       (+ 2 y))))))
 (+ 2 3)
 7)

;; 2
(define-syntax if-all
  (syntax-rules (do else)
    ((if-all (p1 ...) do (e1 ...) else (n1 ...))
     (if (and p1 ...)
         (begin e1 ...)
         (begin n1 ...)))
    ((if-all (p1 ...) do (e1 ...))
     (if (and p1 ...)
         (begin e1 ...)))))

(if-all ((< 1 2) (> 5 3)) do ((display "yes") (newline)) else ((display "no") (newline)))
(if-all ((< 1 2) (> 3 3)) do ((display "yes") (newline)) else ((display "no") (newline)))

;; 3
(define stream-null? null?)

(define multi-map
  (lambda (procs data)
    (if (or (stream-null? procs) (stream-null? data))
        '()
        (cons-stream ((stream-car procs) (stream-car data))
                     (multi-map (stream-cdr procs) 
                                (stream-cdr data))))))

(define display-stream 
  (lambda (s . n)
    (if (not (stream-null? s))
        (if (null? n)
            (begin
              (display (stream-car s))
              (newline)
              (display-stream (stream-cdr s)))
            (if (> (car n) 0)
                (begin
                  (display (stream-car s))
                  (newline)
                  (display-stream (stream-cdr s) (- (car n) 1))))))))

(define ones (cons-stream 1 ones))

(define stream-map
  (lambda (f . streams)
    (if (stream-null? (car streams))
        '()
        (cons-stream
         (apply f (map stream-car streams))
         (apply stream-map f
                (map stream-cdr streams))))))

(define naturals (cons-stream 1
                              (stream-map + ones naturals)))

(display-stream (multi-map powers naturals) 5)
