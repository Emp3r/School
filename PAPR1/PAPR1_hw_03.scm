; 1.
(define count (lambda (l)
                (foldr (lambda (elem accum)
                         (define in?
                           (lambda (hledany l)
                             (cond ((null? l) #f)
                                   ((equal? hledany (caar l)) #t)
                                   (else (in? hledany (cdr l))))))
                         (cond ((null? accum) (cons (cons elem 1) '()))
                               ((in? elem accum) (map (lambda (pair)
                                                        (if (equal? elem (car pair))
                                                            (cons (car pair) (+ 1 (cdr pair)))
                                                            pair))
                                                      accum))
                               (else (cons (cons elem 1) accum))))
                       '()
                       l)))
;(count '(3 1 3 2 1 2 3 3 3))      ; ((3 . 5) (1 . 2) (2 . 2))
;(count '(d b a c b b a))          ; ((d . 1) (b . 3) (a . 2) (c . 1))

; 2.
(define map-index-pred (lambda (pred? f l)
                         (let recur ((i 0)(l l))
                           (cond ((null? l) '())
                                 ((pred? i) (cons (f (car l)) (recur (+ 1 i) (cdr l))))
                                 (else (cons (car l) (recur (+ 1 i) (cdr l))))))))
;(map-index-pred odd? sqr '(2 3 4 5))                   ; (2 9 4 25)
;(map-index-pred (lambda (i) (< i 2)) - '(1 2 3 4 5))   ; (-1 -2 3 4 5)

; 3.
(define catalan1 (lambda (n)
                   (if (= 0 n) 1
                       (* (/ (+ (* 4 n) 2) (+ n 2)) (catalan1 (- n 1))))))
(define catalan2 (lambda (n)
                   (define iter (lambda (i accum)
                                  (if (= i 0) accum
                                      (iter (- i 1) (* accum (/ (+ (* 4 i) 2) (+ i 2)))))))
                   (iter n 1)))
(define catalan3 (lambda (n)
                   (let iter ((i n) (accum 1))
                     (if (= i 0) accum
                         (iter (- i 1) (* accum (/ (+ (* 4 i) 2) (+ i 2))))))))
;(catalan1 1)    ; 2
;(catalan3 1)    ; 2

; 4.
(define euclid1 (lambda (u v)
                  (cond ((< u v) (euclid1 v u))
                        ((= v 0) u)
                        (else (euclid1 v (modulo u v))))))
(define euclid2 (lambda (x y)
                  (define iter (lambda (u v)
                                 (cond ((< u v) (iter v u))
                                       ((= v 0) u)
                                       (else (iter v (modulo u v))))))
                  (iter x y)))
(define euclid3 (lambda (x y)
                  (let iter ((u x) (v y))
                    (cond ((< u v) (iter v u))
                          ((= v 0) u)
                          (else (iter v (modulo u v)))))))
;(euclid1 9 24)     ; 3
;(euclid2 17 25)    ; 1
;(euclid3 5 5)      ; 5

; 5.
(define harmonic-mean (lambda (seznam)
                        (let iter ((n 0) (l seznam) (temp 0.0))
                          (if (null? l) 
                              (/ n temp)
                              (iter (+ n 1) (cdr l) (+ temp (/ 1 (car l))))))))
;(harmonic-mean '(1 2 4))       ; 1.71429...
;(harmonic-mean '(11 12 13))    ; 11.94432...

; 6.
(define divided-by-three? (lambda (l)
                            (let iter ((l l) (stav 'q0))
                              (cond ((null? l) (equal? stav 'q0))
                                    ((equal? stav 'q0) (cond ((= (car l) 0) (iter (cdr l) stav))
                                                             ((= (car l) 1) (iter (cdr l) 'q1))
                                                             ((= (car l) 2) (iter (cdr l) 'q2))))
                                    ((equal? stav 'q1) (cond ((= (car l) 0) (iter (cdr l) stav))
                                                             ((= (car l) 1) (iter (cdr l) 'q2))
                                                             ((= (car l) 2) (iter (cdr l) 'q0))))
                                    ((equal? stav 'q2) (cond ((= (car l) 0) (iter (cdr l) stav))
                                                             ((= (car l) 1) (iter (cdr l) 'q0))
                                                             ((= (car l) 2) (iter (cdr l) 'q1))))))))
;(divided-by-three? '(2 0 2 1 1))        ; #t
;(divided-by-three? '(0 1 2 2 2 2 1))    ; #f



