function [ output ] = average_dithering(obrazek)

    s = size(obrazek);
    output = zeros(s);
    average = mean(mean(obrazek));

    for x = 1:s(1)
        for y = 1:s(2)
            if obrazek(x, y) > average
                output(x, y) = 1;
            end
        end
    end
    
    figure, imshow(output);

end

