candidate should strike a balance between project experience and coding ability

**我更关心的是你做事的方式和沟通问题的能力**
1. 提出问题， 请序列化／反序列化二叉树。  

什么？ 面试者不知道什么是序列化， 反序列化？ 那我就问个多线程爬虫， timing LRU 一类的。 **如果多线程, 锁也不会**， 那说明这个面试者的项目经验很不足。 为了和其他有经验的人相match, 往往我会给一个很open的问题， 或者一个很难的hard coding (取决于他已经被考察了哪个方面). 我个人觉得很少有面试官上来会考你 very hard coding question, 只是当某个面试者与其他面试人比显得相当缺少项目经验， 那他除非能在聪明敏捷或者下苦功方面有突出表现， 否则很难击败其他候选人

a harder problem is given only when you lack project experience

2. Communication stage

如果面试者马上开始写程序， 或者马上给出他的想法， 我会觉得面试者太过于着急了。 在实际项目中， 你很熟悉的地方可能成为项目中的滑铁卢， 因为你熟悉的地方可能有变化， 可能有个big change你不知道， 可能很快你熟悉的api就不工作了。 你总是要有很好的沟通能力， 确保大家知道你在做什么， 及早的发现你的错误。 在code review甚至是test 阶段被人发现问题对项目来说就是个灾难。(the motivation behind clarification)

candidate are expect to ask clarification on constraints (audience for the api) (internal or public!) ( speed or space!) (need multithreading?)

如果面试者没问任何问题马上开始照着leetcode的signature开始coding, 甚至class 名字也叫solution(很多人）， 我会觉得面试者做事的方式不够成熟。 可能以后工作中会很毛躁， 需要人来指导。

give me proper name to the class! ( a mature way of working style!)

无论你提出的建议是什么， 例如你说你觉得speed更重要， 我可能会说我更期待space. 这样做是避免陷入你最熟悉的套路。  假设我说， 我更需要序列化之后的空间占用最小。 这时候一般的候选人不会刷到这么深， 开始思考。
如果候选人根本没有办法优化空间， 那我会认为他give up too early.  我希望候选人能安静的思考， 提出几种方案， 哪怕方案不成立。
如果候选人提出过多的方案， 没有问help, 这些方案也不工作， 我就认为候选人沟通有问题， 无法把握好度。

should communicate valid solution! (understand the most important requirement for the problem!)

4. 候选人选定方案后， 我希望他能和我沟通， 看看是不是在有限时间内能够写完。 项目中， 很多时候谁都知道什么design是正确的， 什么是bad smell, 最聪明的人不是能做出最好design的， 而是在有限时间内能给出大家都能接受的solution的人。  如果空间优化非常好， 但是代码将超级复杂， 无法写完， 那么面试者应该及时和我交流。 我也会偶尔提醒一下这样能不能在40分钟内写完。 如果面试者坚持， 我不会坚持， 我会看他是不是能写完。 这就是either strong hire or fail.

give a workable solution (design) with the given timeframe( comuunicate with the interviewer )

Coding part
6. coding 开始
到这一步， 如果你做了2，3，4，5中的大部分， 并且code看起来似乎是work的， 没有什么致命的问题，我不会纠结于比如正负数， nullpointer, int.min, coding是不是整洁一类的无聊问题。  一般就开始7. 如果你2，3，4，5都skip, 那么一般我就希望你bug free, 否则你没办法和其他候选者做比较。往往bug free又要求你code组织很好， 否则谁都很难一眼看出你有没有问题。


minor errors does not matter, except for companies such as facebook ?
or ask the interviewer if bug free is required? 

7. follow up
还有什么能改进的吗？ 这是考察面试者的知识面， 也看你是不是耐心。 很多时候， 很多面试者做出题目高兴的得意忘形， 到这里就开始语无伦次的乱说， 这不会影响到是不是hire， 但是可能会影响是不是strong hire, 也可能影响到你的level.

举例子： 我觉得还需要写点java doc啊，unit test, regression test, performance tuning, benchmarking, A/B testing, 等等。

take this section seriously( test your knowledge and thinking )

Microsoft: 
1）思路是不是清楚， 写出的code是背下来的一般就没有一个清晰的推理过程， 背题的人写代码和讨论test case和真明白背后原理或者数学证明的是完全不同的。
2)  到底为什么选择做程序员， 未来的规划是什么， 你有没有想清楚自己如何做才能支持自己的职业规划。
3）面试者能不能自己通过走查代码， 加测试用例自己发现自己的bug, fix的对不对， 能不能保证不引发新的regression bug.