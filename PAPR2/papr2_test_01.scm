(define-macro swap
  (lambda (a b)
    (let ((pom (gensym)))
      (if (and (symbol? a)
               (symbol? b))
          `(let ((,pom ,a))
             (set! ,a ,b)
             (set! ,b ,pom))
          (error "Makro pracuje jen se symboly.")))))

;(define pom 5)
;(define y #t)
;(swap pom y)
;pom
;y

;Nefunguje, protože Scheme odděluje makroexpanzi od vyhodnocování výrazů. V teoretickém modelu z přednášky by bylo OK.
;(define-macro dosquares
;  (lambda (n from to . body)
;    `(begin 
;       (let ((,n ,((lambda (x) (* x x)) from)))
;         ,@body)
;       (if (< ,from ,to)
;           (dosquares ,n ,(+ from 1) ,to ,@body)))))

(define-macro dosquares
  (lambda (n from to . body)
    (let ((loop-name (gensym))
          (num (gensym)))
      `(let ,loop-name ((,num ,from))
         (let ((,n (* ,num ,num)))
           ,@body
           (if (< ,num ,to) (,loop-name (+ 1 ,num))))))))

;(dosquares n 5 10 (display n) (display " "))
;(newline)
;(let ((sum 0))
;  (dosquares n 1 10 (set! sum (+ sum n)))
;  sum)

(define cycle!
  (lambda (l)
    (let iter ((aux l))
      (if (null? (cdr aux))
          (set-cdr! aux l)
          (iter (cdr aux))))))

;(define s '(a b c d e))
;(cycle! s)
;s

(define cyclic?
  (lambda (l)
    (let test ((rest (if (null? l)
                         '()
                         (cdr l))))
      (cond ((null? rest) #f)
            ((eq? rest l) #t)
            (else (test (cdr rest)))))))

;(cyclic? '())
;(cyclic? '(a b c))
;(define s '(a b c))
;(cycle! s)
;(cyclic? s)

(define uncycle!
  (lambda (l)
    (let iter ((aux l))
      (if (eq? (cdr aux) l)
          (set-cdr! aux '())
          (iter (cdr aux))))))

;(newline)
;(define t '(1 2 3 4 5 6))
;(cycle! t)
;t

(define clength
  (lambda (clist)
    (if (null? clist) 0
        (let iter ((l (cdr clist))
                   (len 1))
          (if (eq? l clist) len
              (iter (cdr l) (+ len 1)))))))


;(define t '(1 2 3 4 5 6))
;(cycle! t)
;t
;(clength t)
;(clength '()) 

(define cinsert
  (lambda (clist el i)
    (if (= i 0) (error "Prvni prvek nejde vlozit.")
        (let iter ((l clist)
                   (i i))
          (if (= i 1)
              (let ((new-cell (cons el (cdr l))))
                (set-cdr! l new-cell))
              (iter (cdr l) (- i 1)))))
    clist))


;(define t '(1 2 3 4 5 6))
;(cycle! t)
;t
;(cinsert t 'a 2)
;(cinsert t 'b 7)
;(cinsert t 'c 9)
;t