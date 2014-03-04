; set-car! / set-cdr! - sf, přenastaví pouze část páru

(define depth-cyclic?
  (lambda (l)
    (let ((found '()))
      (let test ((l l))
        (if (pair? l)
            (if (memq l found)
#t
                (begin
                  (set! found (cons l found))
                  (or (test (car l))
                      (test (cdr l)))))
#f)))))

(define depth-map! (lambda (fce list)
                     (let ((found '() ))
                       (let map ((l list))
                         (if (pair? l)
                             (if (memq l found)
                                 l
                                 (begin
                                   (set! found (cons l found))
                                   (if (pair? (car l))
                                       (map (car l))
                                       (if (null? (car l))
                                           (car l)
                                           (set-car! l (fce (car l)))))
                                   (if (pair? (cdr l))
                                       (map (cdr l))
                                       (if (null? (cdr l))
                                           list
                                           (set-cdr! l (fce (cdr l))))))))))))

(define t '(1 2 'c 3 () 4 (5 (())) 6 (7 . 8) (9 (9) 9)))
(set-car! (cddr t) t)
(display "t: ")   t
(display "(depth-map! - t): ")   (depth-map! - t)