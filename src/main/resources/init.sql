INSERT INTO category_list (category_name)
VALUES
    ('ELSE'),
    ('Food & Beverage'),
    ('Clothing'),
    ('Electronics'),
    ('Electricity Bill'),
    ('Water Bill')
ON CONFLICT (category_name) DO NOTHING;
