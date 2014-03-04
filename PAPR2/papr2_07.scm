(define-syntax foreach
  (syntax-rules (in do)
    ((foreach var in list do body ...)
     (let loop ((l list))
       (if (not (null? l))
           (let ((var (car l)))
             body ...
             (loop (cdr l))))))))
;(foreach cislo in '(1 4 5 7) do (display cislo) (display " "))   (newline)
;(foreach cislo in '(0 1 2 3 4 5 6 7 8 9) do (display (* cislo cislo)) (display " "))   (newline)

(define-syntax myfor
  (syntax-rules (:= to modified by do)
    ((myfor var := start to end modified by procedure do body ...)
     (let loop ((i start))
       (if (<= i end)
           (let ((var (procedure i)))
             body ...
             (loop (+ i 1))))))))
;(myfor xyz := 5 to 10 modified by (lambda (x) (* x x 2)) do (display xyz) (display " "))   (newline)

;PROC-OR ???
(define-syntax for
  (syntax-rules (in do)
    ((for param in values do body ...)
     (let loop ((l values))
       (if (not (apply proc-or (map null? l)))
           (begin
             (apply (lambda vars body ...)
                    (map car l))
             (loop (map cdr l))))))))
;(for (a b) in '((1 2) (3 4)) do (display a) (display " ") (display b) (newline))

(define-syntax multifor
  (syntax-rules (in do)
    ((multifor ((var in value ...) ...) do body ...)
     (let-syntax ((m-for (syntax-rules (in do)
                           ((m-for vars in lists do stmt)
                            (let loop ((l lists))
                              (if (not (null? (car l)))
                                  (begin
                                    (apply (lambda vars stmt)
                                           (map car l))
                                    (loop (map cdr l)))))))))
       (m-for (var ...) in '((value ...) ...) do (begin body ...))))))
;(multifor ((a in 1 2) (b in 3 4)) do (display a) (display " ") (display b) (newline))

