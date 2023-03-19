INSERT INTO blog_search_results (keyword, search_result, search_type, api_type, created_dt, updated_dt) VALUES ('안녕', '{"name":"이름"}', 'ACCURACY', 'KAKAO', '2020-01-03 11:00:00', '2020-01-03 11:00:00');
INSERT INTO blog_search_results (keyword, search_result, search_type, api_type, created_dt, updated_dt) VALUES ('하이', '{"name":"하이하이"}', 'RECENCY', 'NAVER', '2020-01-03 11:00:00', '2020-01-03 11:00:00');
INSERT INTO blog_search_results (keyword, search_result, search_type, api_type, created_dt, updated_dt) VALUES ('하이222', '{"name":"하이하이"}', 'RECENCY', 'KAKAO', '2020-01-03 11:00:00', '2020-01-03 11:00:00');

INSERT INTO blog_search_keywords (keyword, search_count, created_dt, updated_dt) VALUES ('안녕', 3, '2020-01-03 11:00:00', '2020-01-03 11:00:00');
INSERT INTO blog_search_keywords (keyword, search_count, created_dt, updated_dt) VALUES ('하이', 7, '2020-01-03 11:00:00', '2020-01-03 11:00:00');