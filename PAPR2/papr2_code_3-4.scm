;;;; Pripomenuti

;(define a '(ahoj))
;(set-cdr! a a)
;a

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

;(define s '(a b c))
;(cycle! s)
;(set! s (cdr s))
;s
;(uncycle! s)
;s

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

;(define s '(a b c d e))
;(cycle! s)
;(set-car! (cdr s) s)
;(uncycle! s)
;(depth-cyclic? s)


;;;;; Uloha 1

(define depth-map!
  (lambda (fce list)
    (let ((processed '()))
      (let map ((l list))
        (if (pair? l)
            (if (memq l processed)
                l
                (begin 
                  (set! processed (cons l processed))
                  (cond ((pair? (car l)) (map (car l)))
                        ((null? (car l)) '())
                        (else (set-car! l (fce (car l)))))
                  (cond ((pair? (cdr l)) (map (cdr l)))
                        ((null? (cdr l)) '())
                        (else (set-cdr! l (fce (cdr l)))))))
            (if (null? l) '()
                (error "Argument is not a list.")))))
    list))


;(define t '(1 2 3 4 5))
;(set-car! (cddr t) t)
;t
;(depth-map! - t)

;;;; Prednaska - obousmerne seznamy

(define cons-dlist
  (lambda (elem dlist)
    (let ((new-cell (cons elem
                          (cons '()
                                dlist))))
      (if (not (null? dlist))
          (set-car! (cdr dlist) new-cell))
      new-cell)))

(define dlist-car (lambda (dlist) (car dlist)))
(define dlist-cdr (lambda (dlist) (cddr dlist)))
(define dlist-cir (lambda (dlist) (cadr dlist)))

;;;; Predelani obousmerneho seznamu na objekt

(define make-dlist
  (lambda ()
    (let ((dlist '()))
      
      (define dlist-cons
        (lambda (elem list)
          (let ((new-cell (cons elem
                                (cons '()
                                      list))))
            (if (not (null? list))
                (set-car! (cdr list) new-cell))
            (set! dlist new-cell))))

      (define dlist-car (lambda (dlist) (car dlist)))
      (define dlist-cdr (lambda (dlist) (cddr dlist)))
      (define dlist-cir (lambda (dlist) (cadr dlist)))
      
      (define dlist-insert
        (lambda (el i)
          (if (or (null? dlist)
                  (= i 0))
              (dlist-cons el dlist)
              (let iter ((list dlist)
                         (i i))
                (if (or (= i 1)
                        (null?  (cddr list)))
                    (let ((new-cell (cons el
                                          (cons list
                                                (dlist-cdr list)))))
                      (if (not (null? (cddr list)))
                          (set-car! (cdddr list) new-cell))       
                      (set-cdr! (cdr list) new-cell)) 
                    (iter (dlist-cdr list) (- i 1)))))))
      
      (define dlist-delete
        (lambda (i)
          (if (not (null? dlist))
              (if (= i 0) 
                  (begin 
                    (set! dlist (dlist-cdr dlist))
                    (if (not (null? dlist))
                        (set-car! (cdr dlist) '())))
                  (let iter ((list dlist)
                             (i i))
                    (if (= i 1)
                        (begin 
                          (if (not (null? (dlist-cdr (dlist-cdr list))))
                              (set-car! (cdr (dlist-cdr (dlist-cdr list))) list))
                          (set-cdr! (cdr list) (dlist-cdr (dlist-cdr list)))))
                    (if (and (not (null? (dlist-cdr list)))
                             (not (null? (dlist-cdr (dlist-cdr list)))))
                        (iter (dlist-cdr list) (- i 1))))))))
            
                
      
      (define dlist-print (lambda ()
                            (display "<")
                            (let iter ((l dlist))
                              (if (null? l) '()
                                  (begin
                                    (display (dlist-car l))
                                    (if (not (null? (dlist-cdr l)))(display ", "))
                                    (iter (dlist-cdr l)))))
                            (display ">")
                            (newline)))       
      
      (lambda (signal . args)
        (cond ((equal? signal 'dcar) (dlist-car dlist))
              ((equal? signal 'dcdr) (dlist-cdr dlist))
              ((equal? signal 'dcir) (dlist-cir dlist))
              ((equal? signal 'dcons) (dlist-cons (car args) dlist))
              ((equal? signal 'insert) (dlist-insert (car args) (cadr args)))
              ((equal? signal 'delete) (dlist-delete (car args)))
              ((equal? signal 'print) (dlist-print))
              ((equal? signal 'get) dlist)
              (else (error "unknown signal")))))))

(define test-dlist (make-dlist))
test-dlist
(test-dlist 'print)
(test-dlist 'dcons 1)
(test-dlist 'print)
(test-dlist 'dcons 2)
(test-dlist 'print)
(test-dlist 'dcons 3)
(test-dlist 'insert 5 2)
(test-dlist 'print)
(test-dlist 'delete 4)
(test-dlist 'print)

;;;;; makra if* and a or - pripomenuti

(define-macro if*
  (lambda (test expr . alt)
    `(let (($result ,test))
       (if $result
           ,expr
           ,@alt))))

;(if* 1 2 3)
;(if* 1 $result 3)


(define-macro and
  (lambda args
    (if (null? args)
        #t
        (if (null? (cdr args))
            (car args)
            `(if ,(car args)
                 (and ,@(cdr args))
                 #f)))))

;(and)
;(and 1 2 3)
;(and 1 #f 3)

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

;(or)
;(or 1 2 3)
;(or #f 2 3)

;;;; Uloha - naprogramujte fluid-and

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

;(fluid-and)
;(fluid-and 1 (= $result 1) 3 $result)
;(fluid-and 1 #f 3)

;;;; pripomenuti cond

(define-macro cond
  (lambda clist
    (if (null? clist)
        `(if #f #f)
        (if (equal? (caar clist) 'else)
            (cadar clist)
            `(if ,(caar clist)
                 ,(cadar clist)
                 (cond ,@(cdr clist)))))))

;;;; Uloha - naprogramujte cond*

(define-macro cond*
  (lambda clist
    (if (null? clist)
        `(if #f #f)
        (if (equal? (caar clist) 'else)
            (cadar clist)
            `(let (($result ,(caar clist)))
               (if $result
                 ,(cadar clist)
                 (cond* ,@(cdr clist))))))))

;(cond* ((> 2 2) 2)
;       ((equal? 2 2.) 3)
;       (else 5))
;(cond* ((> 2 2) 2)
;       ((+ 2 2) $result)
;       (else 5))
