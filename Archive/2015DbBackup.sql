--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: oscar; Tablespace: 
--

CREATE TABLE categories (
    id integer NOT NULL,
    name character varying,
    points1 integer,
    points2 integer,
    points3 integer
);


ALTER TABLE public.categories OWNER TO oscar;

--
-- Name: nominees; Type: TABLE; Schema: public; Owner: oscar; Tablespace: 
--

CREATE TABLE nominees (
    id integer NOT NULL,
    category integer,
    name character varying
);


ALTER TABLE public.nominees OWNER TO oscar;

--
-- Name: nominees_id_seq; Type: SEQUENCE; Schema: public; Owner: oscar
--

CREATE SEQUENCE nominees_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.nominees_id_seq OWNER TO oscar;

--
-- Name: nominees_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: oscar
--

ALTER SEQUENCE nominees_id_seq OWNED BY nominees.id;


--
-- Name: playerpicks; Type: TABLE; Schema: public; Owner: oscar; Tablespace: 
--

CREATE TABLE playerpicks (
    player integer,
    category integer,
    toppick integer,
    midpick integer,
    botpick integer
);


ALTER TABLE public.playerpicks OWNER TO oscar;

--
-- Name: players; Type: TABLE; Schema: public; Owner: oscar; Tablespace: 
--

CREATE TABLE players (
    id integer NOT NULL,
    name character varying(200)
);


ALTER TABLE public.players OWNER TO oscar;

--
-- Name: players_id_seq; Type: SEQUENCE; Schema: public; Owner: oscar
--

CREATE SEQUENCE players_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.players_id_seq OWNER TO oscar;

--
-- Name: players_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: oscar
--

ALTER SEQUENCE players_id_seq OWNED BY players.id;


--
-- Name: winners; Type: TABLE; Schema: public; Owner: oscar; Tablespace: 
--

CREATE TABLE winners (
    category integer,
    winner integer
);


ALTER TABLE public.winners OWNER TO oscar;

--
-- Name: id; Type: DEFAULT; Schema: public; Owner: oscar
--

ALTER TABLE ONLY nominees ALTER COLUMN id SET DEFAULT nextval('nominees_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: oscar
--

ALTER TABLE ONLY players ALTER COLUMN id SET DEFAULT nextval('players_id_seq'::regclass);


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: oscar
--

COPY categories (id, name, points1, points2, points3) FROM stdin;
1	Best Picture	15	12	9
2	Best Director	5	4	3
3	Best Actor	5	4	3
4	Best Actress	5	4	3
5	Best Supporting Actor	5	4	3
6	Best Supporting Actress	5	4	3
7	Best Original Screenplay	3	2	1
8	Best Adapted Screenplay	3	2	1
9	Best Animated Feature Film	3	2	1
10	Best Foreign Language Film	3	2	1
11	Best Documentary – Feature	3	2	1
12	Best Documentary – Short Subject	3	2	1
13	Best Live Action Short Film	3	2	1
15	Best Animated Short Film	3	2	1
16	Best Original Score	3	2	1
17	Best Original Song	3	2	1
18	Best Sound Editing	3	2	1
19	Best Sound Mixing	3	2	1
20	Best Production Design	3	2	1
21	Best Cinematography	3	2	1
22	Best Makeup and Hairstyling	3	2	1
23	Best Costume Design	3	2	1
24	Best Film Editing	3	2	1
25	Best Visual Effects	3	2	1
\.


--
-- Data for Name: nominees; Type: TABLE DATA; Schema: public; Owner: oscar
--

COPY nominees (id, category, name) FROM stdin;
1	1	American Sniper
2	1	Birdman or (The Unexpected Virtue of Ignorance)
3	1	Boyhood
4	1	The Grand Budapest Hotel
5	1	The Imitation Game
6	1	Selma
7	1	The Theory of Everything
8	1	Whiplash
9	2	Wes Anderson – The Grand Budapest Hotel
10	2	Alejandro González Iñárritu – Birdman or (The Unexpected Virtue of Ignorance)
11	2	Richard Linklater – Boyhood
12	2	Bennett Miller – Foxcatcher
13	2	Morten Tyldum – The Imitation Game
14	3	Steve Carell – Foxcatcher as John Eleuthère du Pont
15	3	Bradley Cooper – American Sniper as Chris Kyle
16	3	Benedict Cumberbatch – The Imitation Game as Alan Turing
17	3	Michael Keaton – Birdman or (The Unexpected Virtue of Ignorance) as Riggan Thomson
18	3	Eddie Redmayne – The Theory of Everything as Stephen Hawking
19	4	Marion Cotillard – Two Days, One Night as Sandra Bya
20	4	Felicity Jones – The Theory of Everything as Jane Wilde Hawking
21	4	Julianne Moore – Still Alice as Dr. Alice Howland
22	4	Rosamund Pike – Gone Girl as Amy Elliott-Dunne
23	4	Reese Witherspoon – Wild as Cheryl Strayed
24	5	Robert Duvall – The Judge as Judge Joseph Palmer
25	5	Ethan Hawke – Boyhood as Mason Evans, Sr.
26	5	Edward Norton – Birdman or (The Unexpected Virtue of Ignorance) as Mike Shiner
27	5	Mark Ruffalo – Foxcatcher as Dave Schultz
28	5	J. K. Simmons – Whiplash as Terence Fletcher
29	6	Patricia Arquette – Boyhood as Olivia Evans
30	6	Keira Knightley – The Imitation Game as Joan Clarke
31	6	Emma Stone – Birdman or (The Unexpected Virtue of Ignorance) as Sam Thomson
32	6	Meryl Streep – Into the Woods as The Witch
33	7	Birdman – Alejandro González Iñárritu, et al
34	7	Boyhood – Richard Linklater
35	7	Foxcatcher – E. Max Frye and Dan Futterman
36	7	The Grand Budapest Hotel – Wes Anderson and Hugo Guinness
37	7	Nightcrawler – Dan Gilroy
38	8	American Sniper – Jason Hall from American Sniper
39	8	The Imitation Game – Graham Moore from Alan Turing: The Enigma
40	8	Inherent Vice – Paul Thomas Anderson from Inherent Vice
41	8	The Theory of Everything – Anthony McCarten from Travelling to Infinity
42	8	Whiplash – Damien Chazelle from his short film of the same name
43	9	Big Hero 6 – Don Hall, Chris Williams and Roy Conli
44	9	The Boxtrolls – Anthony Stacchi, Graham Annable and Travis Knight
45	9	How to Train Your Dragon 2 – Dean DeBlois and Bonnie Arnold
46	9	Song of the Sea – Tomm Moore and Paul Young
47	9	The Tale of the Princess Kaguya – Isao Takahata and Yoshiaki Nishimura
48	10	Ida (Poland) in Polish  – Paweł Pawlikowski
49	10	Leviathan (Russia) in Russian – Andrey Zvyagintsev
50	10	Tangerines (Estonia) in Estonian and Russian – Zaza Urushadze
51	10	Timbuktu (Mauritania) in French  – Abderrahmane Sissako
52	10	Wild Tales (Argentina) in Spanish  – Damián Szifrón
53	11	Citizenfour – Laura Poitras, Mathilde Bonnefoy and Dirk Wilutsky
54	11	Finding Vivian Maier – John Maloof and Charlie Siskel
55	11	Last Days in Vietnam – Rory Kennedy and Keven McAlester
56	11	The Salt of the Earth – Wim Wenders, Lélia Wanick Salgado and David Rosier
57	11	Virunga – Orlando von Einsiedel and Joanna Natasegara
58	12	Crisis Hotline: Veterans Press 1 – Ellen Goosenberg Kent and Dana Perry
59	12	Joanna – Aneta Kopacz
60	12	Our Curse – Tomasz Śliwiński and Maciej Ślesicki
61	12	The Reaper (La Parka) – Gabriel Serra Arguello
62	12	White Earth – J. Christian Jensen
63	13	Aya – Oded Binnun and Mihal Brezis
64	13	Boogaloo and Graham – Michael Lennox and Ronan Blaney
65	13	Butter Lamp (La Lampe au beurre de yak) – Hu Wei and Julien Féret
66	13	Parvaneh – Talkhon Hamzavi and Stefan Eichenberger
67	13	The Phone Call – Mat Kirkby and James Lucas
68	15	The Bigger Picture – Daisy Jacobs and Christopher Hees
69	15	The Dam Keeper – Robert Kondo and Daisuke Tsutsumi
70	15	Feast – Patrick Osborne and Kristina Reed
71	15	Me and My Moulton – Torill Kove
72	15	A Single Life – Joris Oprins
73	16	The Grand Budapest Hotel – Alexandre Desplat
74	16	The Imitation Game – Alexandre Desplat
75	16	Interstellar – Hans Zimmer
76	16	Mr. Turner – Gary Yershon
77	16	The Theory of Everything – Jóhann Jóhannsson
78	17	"Everything Is Awesome" from The Lego Movie
79	17	"Glory" from Selma
80	17	"Grateful" from Beyond the Lights
81	17	"I'm Not Gonna Miss You" from Glen Campbell: I'll Be Me
82	17	"Lost Stars" from Begin Again
83	18	American Sniper – Alan Robert Murray and Bub Asman
84	18	Birdman – Martin Hernández and Aaron Glascock
85	18	The Hobbit: The Battle of the Five Armies – Brent Burge and Jason Canovas
86	18	Interstellar – Richard King
87	18	Unbroken – Becky Sullivan and Andrew DeCristofaro
88	19	American Sniper – John Reitz, Gregg Rudloff and Walt Martin
89	19	Birdman – Jon Taylor, Frank A. Montaño and Thomas Varga
90	19	Interstellar – Gary A. Rizzo, Gregg Landaker and Mark Weingarten
91	19	Unbroken – Jon Taylor, Frank A. Montaño and David Lee
92	19	Whiplash – Craig Mann, Ben Wilkins and Thomas Curley
93	20	The Grand Budapest Hotel
94	20	The Imitation Game
95	20	Interstellar
96	20	Into the Woods
97	20	Mr. Turner
98	21	Birdman or (The Unexpected Virtue of Ignorance) – Emmanuel Lubezki
99	21	The Grand Budapest Hotel – Robert Yeoman
100	21	Ida – Łukasz Żal and Ryszard Lenczewski
101	21	Mr. Turner – Dick Pope
102	21	Unbroken – Roger Deakins
103	22	Foxcatcher – Bill Corso and Dennis Liddiard
104	22	The Grand Budapest Hotel – Frances Hannon and Mark Coulier
105	22	Guardians of the Galaxy – Elizabeth Yianni-Georgiou and David White
106	23	The Grand Budapest Hotel – Milena Canonero
107	23	Inherent Vice – Mark Bridges
108	23	Into the Woods – Colleen Atwood
109	23	Maleficent – Anna B. Sheppard
110	23	Mr. Turner – Jacqueline Durran
111	24	American Sniper – Joel Cox and Gary D. Roach
112	24	Boyhood – Sandra Adair
113	24	The Grand Budapest Hotel – Barney Pilling
114	24	The Imitation Game – William Goldenberg
115	24	Whiplash – Tom Cross
116	25	Captain America: The Winter Soldier
117	25	Dawn of the Planet of the Apes
118	25	Guardians of the Galaxy
119	25	Interstellar
120	25	X-Men: Days of Future Past
\.


--
-- Name: nominees_id_seq; Type: SEQUENCE SET; Schema: public; Owner: oscar
--

SELECT pg_catalog.setval('nominees_id_seq', 120, true);


--
-- Data for Name: playerpicks; Type: TABLE DATA; Schema: public; Owner: oscar
--

COPY playerpicks (player, category, toppick, midpick, botpick) FROM stdin;
1	1	2	6	4
1	2	10	12	9
1	3	17	18	15
1	4	20	19	22
1	5	26	27	24
1	6	31	32	29
1	7	33	36	35
1	8	38	39	42
1	9	43	46	47
1	10	52	51	50
1	11	55	57	53
1	12	61	58	59
1	13	64	65	63
1	15	68	72	70
1	16	75	73	77
1	17	79	80	78
1	18	86	84	85
1	19	90	89	88
1	20	95	93	96
1	21	99	98	102
1	22	104	105	103
1	23	106	109	107
1	24	113	115	114
1	25	119	118	117
2	1	2	3	5
2	2	11	10	9
2	3	18	17	16
2	4	21	22	20
2	5	28	25	26
2	6	29	31	32
2	7	36	33	37
2	8	42	40	39
2	9	45	43	47
2	10	48	50	49
2	11	53	56	55
2	12	58	60	59
2	13	63	65	64
2	15	70	72	69
2	16	75	73	77
2	17	79	78	82
2	18	86	83	84
2	19	92	90	91
2	20	93	96	95
2	21	98	99	102
2	22	105	103	104
2	23	108	106	109
2	24	113	115	114
2	25	119	118	117
3	1	2	1	3
3	2	9	10	11
3	3	15	17	16
3	4	22	21	20
3	5	26	24	25
3	6	31	32	29
3	7	36	33	34
3	8	38	39	41
3	9	43	45	44
3	10	51	48	52
3	11	57	56	53
3	12	58	60	61
3	13	64	67	65
3	15	71	69	70
3	16	73	75	77
3	17	78	79	80
3	18	86	83	84
3	19	90	88	89
3	20	93	95	97
3	21	99	98	101
3	22	104	105	103
3	23	106	109	107
3	24	112	111	113
3	25	119	118	116
4	1	3	2	7
4	2	10	11	9
4	3	18	17	14
4	4	21	23	19
4	5	28	26	25
4	6	29	31	32
4	7	36	33	35
4	8	42	39	40
4	9	45	43	46
4	10	49	48	51
4	11	53	57	54
4	12	58	62	61
4	13	67	63	64
4	15	70	69	71
4	16	77	74	75
4	17	79	78	81
4	18	83	84	86
4	19	88	89	92
4	20	93	96	95
4	21	98	99	102
4	22	104	105	103
4	23	106	109	108
4	24	112	113	114
4	25	119	117	118
5	1	3	2	7
5	2	11	9	12
5	3	18	17	15
5	4	21	23	20
5	5	28	25	26
5	6	29	32	30
5	7	36	34	37
5	8	39	41	38
5	9	45	43	44
5	10	48	52	50
5	11	53	55	57
5	12	58	61	62
5	13	67	64	65
5	15	70	68	71
5	16	77	74	73
5	17	79	78	82
5	18	83	85	84
5	19	92	91	90
5	20	93	95	94
5	21	98	99	102
5	22	104	105	103
5	23	106	108	107
5	24	112	113	111
5	25	117	119	118
6	1	2	3	1
6	2	11	10	9
6	3	18	14	17
6	4	21	20	23
6	5	28	26	25
6	6	29	31	30
6	7	36	33	34
6	8	42	39	41
6	9	45	43	44
6	10	48	51	49
6	11	53	55	54
6	12	58	59	61
6	13	66	67	65
6	15	70	69	71
6	16	77	73	74
6	17	79	81	80
6	18	83	84	87
6	19	89	88	92
6	20	93	94	95
6	21	98	99	100
6	22	104	103	105
6	23	106	108	107
6	24	112	111	113
6	25	119	117	118
7	1	2	7	3
7	2	11	10	9
7	3	17	18	16
7	4	20	19	22
7	5	26	25	24
7	6	29	32	30
7	7	34	33	37
7	8	41	39	40
7	9	45	44	47
7	10	52	48	50
7	11	56	53	54
7	12	62	61	59
7	13	64	67	65
7	15	72	69	68
7	16	77	74	75
7	17	79	80	78
7	18	85	84	87
7	19	90	89	88
7	20	96	93	95
7	21	98	99	100
7	22	103	105	104
7	23	106	109	108
7	24	112	114	113
7	25	118	119	120
8	1	2	3	5
8	2	10	11	13
8	3	18	17	15
8	4	21	22	19
8	5	28	26	25
8	6	31	30	32
8	7	36	33	34
8	8	42	41	39
8	9	45	46	47
8	10	48	52	49
8	11	53	57	54
8	12	58	61	59
8	13	66	63	64
8	15	69	72	71
8	16	77	74	73
8	17	79	81	82
8	18	83	87	84
8	19	92	88	91
8	20	93	94	97
8	21	98	101	102
8	22	104	105	103
8	23	106	108	107
8	24	112	111	115
8	25	119	117	120
9	1	2	3	6
9	2	11	10	9
9	3	18	17	15
9	4	21	23	19
9	5	28	25	26
9	6	29	32	31
9	7	36	34	33
9	8	39	41	38
9	9	43	45	47
9	10	48	49	50
9	11	54	53	55
9	12	58	60	61
9	13	67	65	63
9	15	70	69	72
9	16	77	73	75
9	17	79	78	82
9	18	83	86	84
9	19	88	92	89
9	20	93	97	96
9	21	98	101	99
9	22	103	105	104
9	23	106	107	110
9	24	112	115	111
9	25	118	119	117
10	1	3	1	6
10	2	10	11	13
10	3	18	17	15
10	4	21	23	19
10	5	28	26	25
10	6	29	31	32
10	7	33	36	37
10	8	38	41	39
10	9	43	44	47
10	10	48	50	51
10	11	53	56	55
10	12	61	60	59
10	13	65	66	63
10	15	70	71	69
10	16	75	74	77
10	17	79	78	82
10	18	86	85	83
10	19	90	88	89
10	20	93	96	95
10	21	99	98	100
10	22	105	104	103
10	23	108	109	106
10	24	112	114	113
10	25	119	120	118
11	1	1	7	2
11	2	10	13	9
11	3	15	16	17
11	4	20	22	23
11	5	26	28	25
11	6	31	30	29
11	7	33	36	34
11	8	38	42	39
11	9	43	44	47
11	10	48	49	52
11	11	56	53	57
11	12	61	62	59
11	13	66	63	65
11	15	71	68	69
11	16	75	73	74
11	17	78	79	80
11	18	86	83	85
11	19	90	88	89
11	20	95	93	94
11	21	99	98	100
11	22	105	104	103
11	23	106	108	109
11	24	111	113	114
11	25	119	118	120
12	1	4	1	5
12	2	10	9	12
12	3	16	18	15
12	4	21	22	20
12	5	26	24	28
12	6	31	30	32
12	7	36	33	37
12	8	39	41	42
12	9	46	43	45
12	10	52	49	50
12	11	53	56	54
12	12	58	60	59
12	13	65	67	64
12	15	71	68	69
12	16	73	74	77
12	17	79	81	82
12	18	85	83	86
12	19	89	91	90
12	20	93	96	94
12	21	100	99	98
12	22	105	104	103
12	23	108	109	110
12	24	113	111	112
12	25	118	117	119
13	1	2	3	7
13	2	10	11	13
13	3	17	18	16
13	4	21	20	22
13	5	28	24	26
13	6	29	32	31
13	7	36	33	37
13	8	39	41	38
13	9	45	43	46
13	10	48	50	51
13	11	55	56	53
13	12	61	60	58
13	13	67	65	63
13	15	70	72	68
13	16	77	73	74
13	17	79	78	82
13	18	83	85	87
13	19	92	89	88
13	20	93	94	96
13	21	98	99	100
13	22	104	105	103
13	23	106	109	108
13	24	112	111	115
13	25	117	119	118
\.


--
-- Data for Name: players; Type: TABLE DATA; Schema: public; Owner: oscar
--

COPY players (id, name) FROM stdin;
1	Diego
2	Frank X
3	Jack
4	Theresa
5	Nathan Hong
6	Coop
7	Larissa
8	Heeyoung An
9	Grant
10	Hannah
11	Pogo
12	Kate K
13	N&M
\.


--
-- Name: players_id_seq; Type: SEQUENCE SET; Schema: public; Owner: oscar
--

SELECT pg_catalog.setval('players_id_seq', 13, true);


--
-- Data for Name: winners; Type: TABLE DATA; Schema: public; Owner: oscar
--

COPY winners (category, winner) FROM stdin;
5	28
23	106
22	104
10	48
13	67
12	58
19	92
18	83
6	29
25	119
15	70
9	43
20	93
21	98
24	115
11	53
17	79
16	73
7	33
8	39
2	10
3	18
4	21
1	2
\.


--
-- Name: categories_pkey; Type: CONSTRAINT; Schema: public; Owner: oscar; Tablespace: 
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- Name: nominees_pkey; Type: CONSTRAINT; Schema: public; Owner: oscar; Tablespace: 
--

ALTER TABLE ONLY nominees
    ADD CONSTRAINT nominees_pkey PRIMARY KEY (id);


--
-- Name: players_pkey; Type: CONSTRAINT; Schema: public; Owner: oscar; Tablespace: 
--

ALTER TABLE ONLY players
    ADD CONSTRAINT players_pkey PRIMARY KEY (id);


--
-- Name: nominees_category_fkey; Type: FK CONSTRAINT; Schema: public; Owner: oscar
--

ALTER TABLE ONLY nominees
    ADD CONSTRAINT nominees_category_fkey FOREIGN KEY (category) REFERENCES categories(id);


--
-- Name: playerpicks_botpick_fkey; Type: FK CONSTRAINT; Schema: public; Owner: oscar
--

ALTER TABLE ONLY playerpicks
    ADD CONSTRAINT playerpicks_botpick_fkey FOREIGN KEY (botpick) REFERENCES nominees(id);


--
-- Name: playerpicks_category_fkey; Type: FK CONSTRAINT; Schema: public; Owner: oscar
--

ALTER TABLE ONLY playerpicks
    ADD CONSTRAINT playerpicks_category_fkey FOREIGN KEY (category) REFERENCES categories(id);


--
-- Name: playerpicks_midpick_fkey; Type: FK CONSTRAINT; Schema: public; Owner: oscar
--

ALTER TABLE ONLY playerpicks
    ADD CONSTRAINT playerpicks_midpick_fkey FOREIGN KEY (midpick) REFERENCES nominees(id);


--
-- Name: playerpicks_player_fkey; Type: FK CONSTRAINT; Schema: public; Owner: oscar
--

ALTER TABLE ONLY playerpicks
    ADD CONSTRAINT playerpicks_player_fkey FOREIGN KEY (player) REFERENCES players(id);


--
-- Name: playerpicks_toppick_fkey; Type: FK CONSTRAINT; Schema: public; Owner: oscar
--

ALTER TABLE ONLY playerpicks
    ADD CONSTRAINT playerpicks_toppick_fkey FOREIGN KEY (toppick) REFERENCES nominees(id);


--
-- Name: winners_category_fkey; Type: FK CONSTRAINT; Schema: public; Owner: oscar
--

ALTER TABLE ONLY winners
    ADD CONSTRAINT winners_category_fkey FOREIGN KEY (category) REFERENCES categories(id);


--
-- Name: winners_winner_fkey; Type: FK CONSTRAINT; Schema: public; Owner: oscar
--

ALTER TABLE ONLY winners
    ADD CONSTRAINT winners_winner_fkey FOREIGN KEY (winner) REFERENCES nominees(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

