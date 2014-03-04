;;; Reseni uloh z minula

;; Naprogramujte display-stream, ktery byl naznacen na prednasce.
;;(define display-stream (lambda (stream . n) ...

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

;; Vytvorte efektivne (pomoci Eratosthenova sita) stream prvocisel.

(define eratosthenes
  (lambda (s)
    (if (stream? s)
        (if (stream-null? s) '()
            (cons-stream (stream-car s) 
                         (eratosthenes (stream-filter (lambda (e)
                                          (> (modulo e (stream-car s)) 0))
                                        (stream-cdr s)))))
        (error "Argument musi byt stream. "))))

;(define primes 
;  (eratosthenes (stream-cdr naturals)))

;; Vytvorte proceduru pro slevani libovolneho mnozstvi streamu.

(define merge-streams
  (lambda args
    (let iter ((streams args))
      (cond 
        ((null? streams) '())
        ((stream-null? (car streams)) (iter (cdr streams)))
        (else (cons-stream (stream-car (car streams))
                           (iter (append (cdr streams) (list (stream-cdr (car streams)))))))))))

;;;Procviceni - kontext, unikova procedura, aktualni pokracovani

;; Hledani kontextu

(+ 2 (- 1) (* 3 4 5))

(((lambda (x y)
    (lambda (z)
      (x (y z))))
  (lambda (x) (- 2 x))
  (lambda (y) (* 3 y)))
 5)

;; Unikova funkce
;; escaper se da doprogramovat konstruktor unikovych funkci
(define *top-level-escaper* #f)
(call/cc
 (lambda (break)
   (set! *top-level-escaper* break)))

(define escaper
  (lambda (f)
    (lambda args
      (*top-level-escaper* (apply f args)))))

; priklady
(escaper *)
((escaper *) 10 20)
(+ 2 ((escaper *) 10 20))

(define p (escaper (lambda (x y) (= x (- y 2)))))
(if (p 2 4)
    aaa
    bbb)

;; Kontinuace - call/cc

; jak se vyhodnoti nasledujici vyrazy?

(+ 5 (call/cc (lambda (x)
                (- 20 (* 2 3)))))


(+ 5 (call/cc (lambda (x)
                (if (> (x 3) 7)
                    (+ x 5)
                    (- x 3)))))

((lambda (x y)
   (call/cc 
    (lambda (f)
      (x (* y y)))))
 (lambda (x) (* 2 x))
 5)

((lambda (x y)
   (call/cc 
    (lambda (f)
      (x (f y)))))
 (lambda (x) (* 2 x))
 5)

((lambda (x y)
   (call/cc 
    (lambda (f)
      (f (x y)))))
 (lambda (x) (* 2 x))
 5)

(define p #f)
((lambda (x y)
   (+ 2
   (call/cc 
    (lambda (f)
      (set! p f)
      (x (* y y))))))
 (lambda (x) (* 2 x))
 5)
(p 3)
(p 7)

(define pr 
  (lambda (x y)
   (call/cc 
    (lambda (f)
      (set! p f)
      (x (* y y))))))
(pr (lambda (x) x) 1)
(p 2)

(+ 3 
   ((lambda (x)
      (call/cc
       (lambda (f)
         (- 5 
            (call/cc
             (lambda (g)
               (f (g x))))))))
    10))

(+ 3 
   ((lambda (x)
      (call/cc
       (lambda (f)
         (- 5 
            (call/cc
             (lambda (g)
               (g (f x))))))))
    10))


(define a
  (lambda (x)
    (call/cc (lambda (f)
               (if (< 0 x) 
                   (begin 
                     (display "ahoj")
                     f))))))
(define b (a 5))
(b 6)
b

(define x #f)

(call/cc (lambda (f)
           (set! x f)))

(+ 2 (x 5))
(* 3 2 (x))

(define c #f)

(define d
  (lambda args
    (let iter ((l args))
      (call/cc 
       (lambda (f)
         (set! c f)
         (display (car l))
         (newline)
         (x)))
       (iter (cdr l)))))

(d 1 2 3 4 5 6 7)
(c)
(c)
(newline)         