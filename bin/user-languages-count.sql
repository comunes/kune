SELECT l.english_name, count(*) n_users FROM kusers u,globalize_languages l WHERE u.language_id=l.id GROUP BY l.id ORDER BY n_users DESC;
