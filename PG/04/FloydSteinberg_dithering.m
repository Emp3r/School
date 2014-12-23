function [ output ] = FloydSteinberg_dithering(obrazek)
    
    s = size(obrazek);
    output = zeros(s(1), s(2));
    
    for x = 1:s(1)
        for y = 1:s(2)
            oldpixel = obrazek(x, y);
            
            if oldpixel > 255/2
                newpixel = 255;
            else
                newpixel = 0;
            end
            
            error = oldpixel - newpixel;
            output(x, y) = newpixel;
            
            if (x + 1) <= s(1)
                obrazek(x + 1, y) = obrazek(x + 1, y) + error * (7 / 16);
            end
        
            if (y - 1) >= 1
                obrazek(x, y - 1) = obrazek(x, y - 1) + error * (5 / 16);
            end

            if (x - 1) >= 1 && (y - 1) >= 1
                obrazek(x - 1, y - 1) = obrazek(x - 1, y - 1) + error * (3 / 16);
            end

            if (y - 1) >= 1 && (x + 1) <= s(1)
                obrazek(x + 1, y - 1) = obrazek(x + 1, y - 1) + error * (1 / 16);
            end
        end
    end
    
    figure, imshow(output);

end

