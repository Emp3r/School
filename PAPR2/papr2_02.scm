; begin - speciální forma, (begin <v_1> .... <v_n>), vyhodnotí se poslední výraz <v_n>, ty ostatní mají smysl jen když mají vedlejší efekt
; set! - speciální forma, (set! <symbol> <výraz>), hledá první vazbu v hiearchii prostředí, první vazbu kterou najde přenastaví na <výraz>
; fluid-let - sf, (fluid-let ((<s1> <v1>) .. (<sn> <vn>)) <tělo>), udělá (set! <s1> <v1>) až (set! <sn> <vn>) a pak <tělo>

(define s1 0)
(define s2 0)
(define nasobeni (lambda x 
                       (let ((vysledek (apply * x)))
                         (begin
                           (set! s1 (+ s1 1))
                           (if (not (= vysledek 0)) (set! s2 (+ s2 1)))
                           vysledek))))
(display "(nasobeni): ") (nasobeni)
(display "(nasobeni 2 2 2 2 2): ") (nasobeni 2 2 2 2 2)

(newline)
(define na2 (lambda (x) (* x x)))

(define my_expt (lambda (x n)
                  (if (= n 0) 1
                      (if (even? n) 
                          (na2 (my_expt x (/ n 2)))
                          (* x (my_expt x (- n 1)))))))
(display "(my_expt 2 5): ")
(my_expt 2 5)

(newline)
(define citac 0)
(define * (let ((* *))
            (lambda x
              (set! citac (+ citac 1))
              (apply * x))))
(* 3 4 5) citac
(* 2 6) citac
(*) citac
