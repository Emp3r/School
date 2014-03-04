(define-syntax for
  (syntax-rules (:= to downto do step)
    ((for var := start to end do stmt ...)
     (let loop ((var start))
       (if (<= var end)
           (begin
             stmt ...
             (loop (+ var 1))))))
    ((for var := start downto end do stmt ...)
     (let loop ((var start))
       (if (>= var end)
           (begin
             stmt ...
             (loop (- var 1))))))
    ((for var := start to end step inc do stmt ...)
     (let loop ((var start))
       (if (<= var end)
           (begin
             stmt ...
             (loop (+ var inc))))))
    ((for var := start downto end step dec do stmt ...)
     (let loop ((var start))
       (if (>= var end)
           (begin
             stmt ...
             (loop (- var dec))))))))

;(for i := 1 to 5 do (display i) (newline))

(define-syntax foreach
  (syntax-rules (in do)
    ((foreach var in list do stmt ...)
     (let loop ((l list))
       (if (not (null? l))
           (let ((var (car l)))
             stmt ...
             (loop (cdr l))))))))

;(foreach cislo in '(1 4 5 7) do (display cislo) (newline))

(define-syntax myfor
  (syntax-rules (:= to modified by do)
    ((myfor var := from to end modified by proc do stmt ...)
     (let loop ((i from))
       (if (<= i end)
           (let ((var (proc i)))
             stmt ...
             (loop (+ i 1))))))))

;(myfor xyz := 5 to 10 modified by (lambda (x) (* x x 2)) do (display xyz) (newline))

(define proc-or
  (lambda args
    (if (null? args) #f
        (if (car args) #t
            (apply proc-or (cdr args))))))

;(proc-or)
;(proc-or #f #f 1)

(define-syntax for
  (syntax-rules (in do)
      ((for vars in lists do stmt1 ...)
       (let loop ((l lists))
         (if (not (apply proc-or (map null? l)))
             (begin
               (apply (lambda vars
                        stmt1 ...)
                      (map car l))
               (loop (map cdr l))))))))
   
;(for (a b) in '((1 2) (3 4)) do (display a) (newline) (display b) (newline))

(define-syntax multifor
  (syntax-rules (in do)
    ((multifor ((var in value ...) ...) do statement ...)
       (for (var ...) in '((value ...) ...) do statement ...))))

;(multifor ((a in 1 2) (b in 3 4)) do (display a) (newline) (display b) (newline))

(define-syntax multifor
  (syntax-rules (in do)
    ((multifor ((var in value ...) ...) do statement ...)
     (let-syntax ((m-for (syntax-rules (in do)
                           ((m-for vars in lists do stmt)
                            (let loop ((l lists))
                              (if (not (apply proc-or (map null? l)))
                                  (begin
                                    (apply (lambda vars
                                             stmt)
                                           (map car l))
                                    (loop (map cdr l)))))))))
       (m-for (var ...) in '((value ...) ...) do (begin statement ...))))))


;(multifor ((a in 1 2 10) (b in 3 4 5)) do (display (+ a b)) (newline))