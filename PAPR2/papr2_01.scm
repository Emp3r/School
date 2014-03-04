(define na2
  (lambda (x) (* x x)))                  ; PG: na2 ->  <(x), (* x x), PG>

(((lambda (x y)                          ; <(x y), (lambda (y z) (+ (x (na2 y) (na2 z))), PG>
    (lambda (y z)                        ;
      (+ (x (na2 y)) (na2 z))))          ;
  ((lambda (f g)                         ; [<(f g), (lambda (x) (f (f (g x)))), PG>, (lambda (x) (+ 2 x), na2]
     (lambda (x)                         ;  - vznikne nové prostředí P1: f -> <(x), (+ 2 x), PG> ; g -> <(x), (* x x), PG>
       (f (f (g x)))))                   ; P1: <(x), (f (f (g x)), P1>
     (lambda (x)                         ; P2: x ->  <(x), (f (f (g x))), P1> ; y ->  2
       (+ 2 x))                          ; P3: y ->  5 ; z ->  4
     na2)                                ; 
  2)                                     ; PG: x ->  625
 5
(* 2 2))                                 ; 645

(define funkce                           
  (lambda (f n . args)                   
    (let iter ((list args)               
               (num n)                   
               (out '() ))               
      (if (null? list) out
          (iter (cdr list) (- num 1)
                (cons (cons (f (car list))
                            num)
                      out))))))
(funkce na2 5 1 2 3)

(define app
  (lambda (args)
    (if (null? args)
        '()
        `(,@(map (caar args)
                 (cdar args))
          ,@(app (cdr args))))))
(app `((,na2 1 2 3 4 5)
       (,(lambda (x) (+ x 10)) 2 3 5 6)))

(define xyz
  (lambda args
    (let ((f (car args))
          (g (cadr args))
          (rest (cddr args)))
      (let* ((fg (lambda (x) (f (g x))))
             (negfg (lambda (x) (- (fg x)))))
        (let fce ((l rest)
                  (out '(vysledky)))
          (if (null? l) out
              (if (odd? (length l)) '()
                  (fce (cddr l)
                       (append out (list negfg (car l)))))))))))

;(create-table 'ctverec `(,(lambda (x) (* 4 x))
;                         ,(lambda (x) (* x x)))
;              '(1 2 3 4 5))

; -> ((ctverec 1 2 3 4 5)
;     (obvod 4 8 12 16 20)
;     (obsah 1 4 9 16 25))