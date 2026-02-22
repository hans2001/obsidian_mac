q1
char name and books the appear in, (char naem and bk id, and bk title! ) and order ersult with book id! 
char name at ole_trimmed, and we use role_in_bk to match bk and then get the bk title! finally we order result table by orderid

select r.name,b.bk_number,b.title 
form role_rtrimmed as r
join role_in_book as rib on r.id  = rib.role_id 
join book b on rib.bok_id = b.book_number
order by b.book_number asc

verification:
once there are a valid pari of role_id to book_id, should be a row in the result table , that is why we cna just count total number of rows in role_in_book !

(doing inner join since we have to check if role_id exist, but that is not necessary  )

q2
we used count, count is aggreagator, that is why we need group by clause, and potentily all select fields shoudl eb in group by, except the count? 
all house should present even without char ,that is why we need left join 

select h.name, count( r.id ) as num_students
from role_trimmed as r
left join hogwart_house as h on h.name = r.house
group by h.name
order by num_students desc

ver: 
should be equal to total number of characters? (each char must belong to some house!)
seelct count(*) from role trimmed r where r.house in (select name from hogwart_house) as total_with_house (total charactersr that belong to any house) 
compare it with the response 

q3:
join role to patrouns with role_to_patrons(n to n)
select p.name, count of char and rename count  as num_char, then sort results in desc using count num_char? 
all patronus should present so we left join patronous! 

select p.patronus_shape, count( r.id ) as num_characters
from patronus p 
left join role_to_patronus rtp on rtp.patronus = p.patronus_shape
group by p.patronus_shape
order by num_characters desc

vef, response coutn shoudl equal to tootal numerbof pairs in role_to_p? 
with counts as (
select p.patronus_shape, count( r.id ) as num_characters
from patronus p 
left join role_to_patronus rtp on rtp.patronus = p.patronus_shape
group by p.patronus_shape
)

select 
SELECT COALESCE(SUM(c),0) FROM counts) AS total_from_answer,
select cont ( * ) form role_to_patronus rp join patronus p on p. shape = rp.patronus as total valid ... 


q4: 
record for char that associated with the gryffindor house, tuple should contain all fieles form role_trimmed, give it a name call gryffindor asso, tabe cna ccraet once only

create table  if not exist gryffindor_association as 
select  * form role_trimmed where house LIKE *%Gryffindor%'

total number of rows here should match total number of rows after we filter by house! 
select count( * ) form yffindor_as oas table_Rwos
select count( * ) from role_trimmed where house like ...


**q5:** 
return tuple for each spell performed by a char, each tuple in result should contain the spell name as spellname , and we have spell type, char name, house, and gender as well so we match role_trimmed with spell using role_to_spell

how should we verify <: make sure all unique pairs? 
vef query can be improved :
SELECT rs.role_id, rs.spell_id, COUNT(*) AS dup_count
FROM role_to_spell rs
JOIN spell s        ON rs.spell_id = s.id
JOIN role_trimmed r ON rs.role_id  = r.id
GROUP BY rs.role_id, rs.spell_id
HAVING COUNT(*) > 1;

q6: 
what is aggregated field( we use group by) what keyword to aggregate 
have a list in the column? 
we get the house form the character? but get the what is the bookNumber is id! ,we can just join the role_to_book to get the id, no need joining the book table? 
rename the grouped list field result to books_in! order results in ascending order by char name

select r.name, r.house, r.eye_color, r.hai_color, group_concat (distinct rib.book_id order by rib.book_id) as books_in 
from role_trimmed as r 
left join role_to_book as rib on rib.role_id = r.id
group by r.name, r.house, r.eye_color, r.hai_color
order by r.name

each role should appear, even it might appar in zero books! 

vef: each list should contain no duplicate valie ! 
we should 

q7: 
which bk has most number of roles (not char for word )? how do we get char count ?result contain book number name and url
we join book with role_ _to book and then aggreate the role id by book  as a count_
then we cna sort result with taht count, and return along with book id namean durl! 
each book should appear even if it shares 0 char 

we need the max count , so single row, we dont want a table sorted by the role_count 

with counts as ( 
select b.name, b.book_number, b.cover_url, count(rib.role_id) as role_count
from book as b
join role_in_book as rib on rib.book_id = b.book_number
group by b.book_number, b.name, b.cover_url
) 

now role_count is single field

select book_number, title, cover_url
from counts
where role_count  = (select max( role_count ) form counts ) (this is filter : not aggregate)
order by book_number

how to verify query 7 
check if there are larger role_count with book

q8:
reverse of 16 ,we should left join bok so that every book appear  in table ,and we shoudl return book number  book name cover url and count of number of chars! then rename count to num_chars! all bks should appear in result? then order reesult in desc order by num_chars!

select b.name, b.book_number, b.cover_url , count( distinct rib.role_id ) as num_chars
from role_in_ book  as rib
left join book  as b  on  b.book_number = rib.book_id 
group by b.book_number
order by num_chars desc

vef: 
aggregate  distinct role book pari ,then verify if the aggregated count equsl to the original distinct role_book pair in the source table! 


q9: 
group spell by spell type then count! rename number of spells to num-spell , then sort result in desc using num-spells, amke sure all spell type in result so we left join spell type table? 

select s.spell_type, count(*) as num_spells
from spell as s
group by s.spell_type
order by num_spells desc

verify if all spells are in the result table by aggregating the spell count per type with the total number of spells in spell table? 

with spell_count  as (
select s.spell_type, count(*) as num_spells
from spell as s
group by s.spell_type
)

select
(select count( * )) (total number of spell in source table!)
(select Coalesce( sum( num_spells) , 0 ) from spell_count )   (total number of spel in result query using the sum method to aggregate the num_spells column!)

the 2 value should be equal


q10: 
first check if char used spell type curse, then check if char is at least male or (red head student that asso with Slytherin or gryffindor) result should contain char_name, birth date, death data house and spell name! 

use the where clause ,and clause to match desired candidate! 

select r.name as char_name ,r. birth, r.death, r.house, s.name as spell_name
form role_trimmed as r
join role_to_spell as rs on rs.role_id = r.id
join spell as s on s.id = rs.spell_id
where lower ( s.spell_type ) == 'curse'
and ( lower( r.gender ) == 'male' or lower( r.hair_color )  like '%red%' ) 
and ( r.house like '%Gryffindor%' or r.house  like  '%Slytherin%' )


**q11:** 
**did not get the spell name for the result table!** 

spell name ,book number when spell occurred, book title, char name and char house 
so char table, spell table (used forwhere caluase to filter)
order by book_number
by inner joining the spell table, we knwo that the book must contain certain spell throug the role_to_spell

role should be joining spell and book? where coud we get char name: (role in book! )
then spell(join role to spell and spell > )

select b.title, b.id, r.name, r.house, s.name as spell_name
from role_trimmed as r 
join role_to_spell  rs on rs.role_id = r.id 
join rol_in_book rib on rib.role_id = r.id
join book b  on b.book_number = rib.book_id 
join spell s on s.id = rs.spell_id
order by b.book_number asc;

vef: 


q12:
we have  a subquery that has all the spell  first, 
then use where clause to filter character that performed all the spell mentioned! 
we cannot use where clause, since result is groupby r.di!  ,so we shoudl use having! 


with all_spell as( 
select count( * ) from spell
)

select r.name, as char_name
form role_trimmed as r
join role_to_spell rs on rs.role_id = r.id
group by r.id , r.name
having count (distinct rs.spell_id) = all_spell


q13: 
char id , char name and house , gender that appear in all books! 
order resutl by char name

subquery all books? 
aggregate char appear in all book? 
having ? 
we can have appear in bks list or? 


here we map role_id t obook_id ,so when after group by role_id, if we do count( distinct rib.book_id ),  the column will have a number indicate how many book this char appear in ,and it shoyld match total number of distinct bks to fit the qeury!
select r.id, r.name,r.house and r.gender 
from role_trimmed r 
join role_in_book rib on rib.role_id = r.id
group by r.id, r.name r.house r .gender
having count( distinct rib.book_id ) = (select count(*) form book)


q14: 
do we aggregate? 
return char id name gneder house eye color and hair color 
useing ahvign to filter, we ned to join role_to_patronus
and count( distinct rtp.patrnous ) > 1 

select r.id,
  r.name AS character_name,
  r.gender,
  r.house,
  r.eye_color,
  r.hair_color
from role_trimmed as r
join role-to-patrnosnu as rtp on rtp.role_id ion = r.id
group by r.name  r.gender, r.house, r.eye_color, r.hair_color
count( distinct rtp.patrnous ) > 1 

vef: 


q15 :
select
  b.book_number,
  b.title,
  r1.name AS character1_name,
  r2.name AS character2_name
FROM role_in_book rib1

JOIN role_in_book rib2 
  ON rib1.book_id = rib2.book_id          -- same book
 AND rib1.role_id < rib2.role_id  

JOIN role_trimmed r1 ON r1.id = rib1.role_id
JOIN role_trimmed r2 ON r2.id = rib2.role_id

WHERE r1.house LIKE '%Hufflepuff%' 
  AND r2.house LIKE '%Hufflepuff%'

17 **. (5 Points) Return the number of potions that are antidotes.  Use the name field to determine if a potion is an antidote. Rename the count to num_antidotes.**

18. (5 Points) Return the id and the name of the character who performs the most number of spells. The result should contain the character id, character name and the count of spells, renamed to num_spells. If there are multiple characters with the maximum, report on all characters. 

19. (5 points) Determine the number of characters associated with a gender. The result should contain the gender name and the count of characters. Rename the count of characters as num_characters. Order the results by num_characters. You only need to report on the genders found in the role_trimmed table. 

20. (5 points) Determine the characters from the Slytherin house who have performed a spell which is classified as a “Healing Spell”. Return the character’s name.
