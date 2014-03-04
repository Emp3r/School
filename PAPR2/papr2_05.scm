(define-macro if*
  (lambda (test expr . alt)
    `(let (($result ,test))
       (if $result
           ,expr
           ,@alt))))
(if* 1 2 3)
(if* 1 $result 3)

(define-macro and
  (lambda args
    (if (null? args)
        #t
        (if (null? (cdr args))
            (car args)
            `(if ,(car args)
                 (and ,@(cdr args))
                 #f)))))
(and)
(and 1 2 4)

; FLUID-AND
(define-macro fluid-and
  (lambda args
    (if (null? args)
        #t
        (if (null? (cdr args))
            (car args)
            `(let (($result ,(car args)))
               (if $result
                   (fluid-and ,@(cdr args))
                   #f))))))
(fluid-and)                              ;   -> #t
(fluid-and 1 (= $result 1) 3 $result)    ;   -> 3
(fluid-and 1 #f 3 $result)               ;   -> #f


(define-macro or
  (lambda args
    (if (null? args)
        #f
        (if (null? (cdr args))
            (car args)
            `(let ((result ,(car args)))
               (if result
                   result
                   (or ,@(cdr args))))))))
(or)
(or #f 10 #f 9)

(define-macro cond
  (lambda clist
    (if (null? clist)
        `(if #f #f)
        (if (equal? (caar clist) 'else)
            (cadar clist)
            `(if ,(caar clist)
                 ,(cadar clist)
                 (cond ,@(cdr clist)))))))
(cond (#f #f)
      ((= 1 1) 1))

; COND*
(define-macro cond*
  (lambda cond-list
    (if (null? cond-list)
        `(if #f #f)
        (if (equal? (caar cond-list) 'else)
            (cadar cond-list)
            `(let (($result ,(caar cond-list)))
               (if $result
                   ,(cadar cond-list)
                   (cond* ,@(cdr cond-list))))))))
(cond* ((> 2 2) 2)
       ((+ 2 2) (/ 10 $result))         ;   -> (/ 10 4)  ->  2,5
       (else 5))