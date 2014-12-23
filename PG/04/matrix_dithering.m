function [ output ] = matrix_dithering(obrazek)
    
    s = size(obrazek);
    output = zeros(s(1) * 2, s(2) * 2);
    
    for x = 1:s(1)
        for y = 1:s(2)
            xx = x * 2;
            yy = y * 2;
            
            if obrazek(x, y) <= (255 / 5)
                output(xx, yy) = 0;
                output(xx + 1, yy) = 0;
                output(xx, yy + 1) = 0;
                output(xx + 1, yy + 1) = 0;
            elseif obrazek(x, y) <= (2 * 255 / 5)
                output(xx, yy) = 0;
                output(xx + 1, yy) = 0;
                output(xx, yy + 1) = 0;
                output(xx + 1, yy + 1) = 1;
            elseif obrazek(x, y) <= (3 * 255 / 5)
                output(xx, yy) = 0;
                output(xx + 1, yy) = 1;
                output(xx, yy + 1) = 0;
                output(xx + 1, yy + 1) = 1;
            elseif obrazek(x, y) <= (4 * 255 / 5)
                output(xx, yy) = 0;
                output(xx + 1, yy) = 1;
                output(xx, yy + 1) = 1;
                output(xx + 1, yy + 1) = 1;
            elseif obrazek(x, y) <= (5 * 255 / 5)
                output(xx, yy) = 1;
                output(xx + 1, yy) = 1;
                output(xx, yy + 1) = 1;
                output(xx + 1, yy + 1) = 1;
            end
        end
    end
    
    figure, imshow(output);

end
