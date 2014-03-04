; 1. 
(/ (+ (log e) 1) 
   (+ (sqrt 4) (/ 10 (+ -1 6))))

; 2.
(define w -)
(define x +)
(define y 1)
(define z 2)
;(x y z)             ; 3
;(w y (x y y))       ; -1

; 3.
(define pyramid (lambda (d v) 
                  (let ((a (* (/ (sqrt 2) 2) d))) 
                    (* a (+ a (sqrt (+ (* 4 v v) (* a a))))))))
;(pyramid 1 1)       ; 2.0

; 4.
(define my-negative? (lambda (x) 
                       (if (> 0 x) #t #f)))
;(my-negative? -2)   ; #t
;(my-negative? 4)    ; #f

; 5.
(define my-proc (lambda (x)
                  (cond ((= x 0) 0)
                        ((> x 0) (+ x 2))
                        ((< x 0) (- x 2)))))
;(my-proc 1)         ; 3
;(my-proc 0)         ; 0
;(my-proc -1)        ; -3

; 6.
(define implies (lambda (a b)
                  (if (and a (not b)) #f #t)))
;(implies #f #f)      ; #t
;(implies #f #t)      ; #t
;(implies #t (> 2 3)) ; #f
;(implies + (< 2 3))  ; #t

; 7.
(define my-even (lambda (x)
                  (and (= x (* 2 (quotient x 2))) x)))
;(my-even 2)          ; 2
;(my-even -4)         ; -4
;(my-even 1)          ; #f
;(my-even -5)         ; #f