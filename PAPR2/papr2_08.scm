(define-macro cons-stream
  (lambda (x y) 
    `(cons ,x (delay ,y))))
(define cdr-stream
  (lambda (s) (force (cdr s))))

(define stream-filter
  (lambda (pred str)
    (let ((s str))
      (cons-stream (let iter ()
                     (if (not (null? s))
                         (if (pred (car s))
                             (car s)
                             (begin 
                               (set! s (cdr-stream s))
                               (iter)))
                         '()))
                     (begin
                       (set! s (cdr-stream s))
                       (stream-filter pred s))))))

(define a (cons-stream (+ 1 2) (cons-stream (+ 4 4) (cons-stream (+ 4 6) '()))))

(define nekonecny
  (let iter ((x 1))
    (cons-stream x (iter (+ x 1)))))

(define licha (stream-filter odd? nekonecny))
;(cdr-stream (cdr-stream (cdr-stream licha)))

(define stream-map
  (lambda (proc str)
    (if (not (null? str))
        (cons-stream (proc (car str))
                     (stream-map proc (cdr-stream str)))
        '())))

;(cdr-stream (cdr-stream (stream-map (lambda (x) (* x x)) licha)))

(define expt-stream
  (let iter ((n 0))
    (cons-stream (lambda (x) (expt x n))
                 (iter (+ n 1)))))

;((car expt-stream) 2)
;((car (cdr-stream (cdr-stream (cdr-stream expt-stream)))) 2)

(define display-stream
  (lambda (str . n)
    (display (car str))
    (if (not (null? n))
        (let iter ((i 1) (s (cdr-stream str)))
          (if (< i (car n))
              (begin
                (display ", ")
                (display (car s))
                (iter (+ i 1) (cdr-stream s)))))
        (let iter ((i 1) (s (cdr-stream str)))
          (if (< i 100)
              (begin
                (display ", ")
                (display (car s))
                (iter (+ i 1) (cdr-stream s))))))
    (newline)))
  
;(display-stream nekonecny)
;(display-stream (stream-map (lambda (x) (* x x)) nekonecny) 10)