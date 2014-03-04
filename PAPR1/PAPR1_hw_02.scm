; 1.
(define my-cons (lambda (x y) (lambda (true?) (if true? x y))))
(define my-car (lambda (x) (x #t)))
(define my-cdr (lambda (x) (x #f)))
;(define p1 (my-cons 3 8))
;(p1 #t)                   ; 3
;(p1 #f)                   ; 8
;(my-car p1)               ; 3
;(my-cdr p1)               ; 8

; 2.
(define switch (lambda (x) (my-cons (x #f) (x #t))))
;(define p1 (my-cons 3 8))
;(define p2 (switch p1))
;(my-car p2)               ; 8
;(my-cdr p2)               ; 3
     
; 3.
(define make-c cons)
(define real car)
(define imag cdr)
(define conj (lambda (x) (cons (car x) (- (cdr x)))))

(define c+ (lambda (x y) (cons (+ (car x) (car y)) (+ (cdr x) (cdr y)))))
(define c- (lambda (x y) (cons (- (car x) (car y)) (- (cdr x) (cdr y)))))
(define c* (lambda (x y) (cons (- (* (car x) (car y)) (* (cdr x) (cdr y)))
                               (+ (* (car x) (cdr y)) (* (cdr x) (car y))))))
(define c/ (lambda (x y) (let ((d (+ (sqr (car y)) (sqr (cdr y))))) 
                           (cons (/ (+ (* (car x) (car y)) (* (cdr x) (cdr y))) d)
                                 (/ (- (* (cdr x) (car y)) (* (car x) (cdr y))) d)))))
;(define c1 (make-c 1 2))
;(define c2 (make-c 3 -1))
;c1                        ; (1 . 2)
;(real c1)                 ; 1
;(imag c1)                 ; 2
;(conj c1)                 ; (1 . -2)
;(c+ c1 c2)                ; (4 . 1)
;(c* c1 c2)                ; (5 . 5)
  
; 4.
(define singletons (lambda (x) (map list x)))
;(singletons '(2))         ; ((2))
;(singletons '(2 -1 3))    ; ((2) (-1) (3))
  
; 5.
(define upresnit (lambda (x) (rationalize (inexact->exact x) 1/10)))

(define roots-of-unity (lambda (x) 
                         (build-list x (lambda (y) (make-rectangular (upresnit (cos (/ (* 2 pi y) x)))
                                                                     (upresnit (sin (/ (* 2 pi y) x))))))))
;(roots-of-unity 2)        ; (1 -1)
;(roots-of-unity 6)        ; (1 1/2+4/5i -1/2+4/5i -1 -1/2-4/5i 1/2-4/5i)
  
; 6.
(define div-list (lambda (x) 
                   (build-list x (lambda (i) (= (modulo x (+ i 1)) 0)))))
;(div-list 2)              ; (#t #t)
;(div-list 3)              ; (#t #f #t)
;(div-list 4)              ; (#t #t #f #t)
;(div-list 5)              ; (#t #f #f #f #t)
;(div-list 12)             ; (#t #t #t #t #f #t #f #f #f #f #f #t)
  
; 7.
(define make-palindrom (lambda (x) 
                         (define len (length x)) 
                         (define sudy (= (modulo len 2) 0))
                         (build-list (if sudy (* 2 len) (- (* 2 len) 1))
                                     (lambda (i) (if (< i len) (list-ref x i) 
                                                     (list-ref x (- (* 2 len) i (if sudy 1 2))))))))
;(make-palindrom '(a n))           ; (a n n a)
;(make-palindrom '(r o t))         ; (r o t o r)
;(make-palindrom '(d e n n i s))   ; (d e n n i s s i n n e d)
;(make-palindrom '(n e p o t))     S; (n e p o t o p e n)
  
; 8.
(define map-index-pred (lambda (pred? f l) 
                         (map (lambda (x y) (if (pred? x) (f y) y)) 
                              (build-list (length l) (lambda (i) i)) 
                              l)))
;(map-index-pred odd? sqr '(2 3 4 5))   ; (2 9 4 25)

; 9.
(define swap-index (lambda (i j l) 
                     (map (lambda (x y) (cond ((= x i) (list-ref l j))
                                              ((= x j) (list-ref l i))
                                              (else y)))
                          (build-list (length l) (lambda (i) i))
                          l)))
;(map-index-pred (lambda(i) (< i 2)) - '(1 2 3 4 5))   ; (-1 -2 3 4 5)