function [ output ] = random_dithering(obrazek)

    s = size(obrazek);
    output = zeros(s);
    
    for i = 1:s(1)
        for j = 1:s(2)
            rand = randi([0, 255], 1);
            
            if obrazek(i, j) < rand
                output(i, j) = 0;
            else
                output(i, j) = 1;
            end
        end
    end

    figure, imshow(output);
    
end

