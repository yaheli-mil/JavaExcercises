import java.util.Scanner;

import javax.security.auth.Subject;

import org.w3c.dom.ranges.Range;

public class QueueExercises {

    public static int sumQueue(Queue<Integer> q) {
        Queue<Integer> temp = new Queue<Integer>();
        int sum = 0;
        
        while (!q.isEmpty()) { 
            int val = q.remove();
            sum = sum + val;
            temp.insert(val);
        }
        while (!temp.isEmpty()) {
            q.insert(temp.remove());
        }
        return sum;
    }
 
    public static boolean secret(Queue<Integer> q, int val) {
        Queue<Integer> temp = new Queue<Integer>();
        boolean found = false;
        while (!q.isEmpty()) {
            int current = q.remove();
            if (current == val) 
                found = true;
            temp.insert(current);
        }
        while (!temp.isEmpty()) {
            q.insert(temp.remove());
        }
        return found;
    }
    // 

    // q1: What value comes from the function -> secret2(q1: 1->2->3, q2: 1->2->3->4)???
    // q2: What value comes from the function -> secret2(q1: 1->2->3, q2: 1->2->3)???
    // q3: What is the porpuse of the function secret2?
   
    // q1: q2: 4
    // tmp1: 1,2,3 tmp2: 1,2,3
    // identical: false
    // v1: 3
    // v2: 3
    public static boolean secret2(Queue<Integer> q1, Queue<Integer> q2) {
        Queue<Integer> tmp1 = new Queue<Integer>();
        Queue<Integer> tmp2 = new Queue<Integer>();
        boolean identical = true;

        while (!q1.isEmpty() && !q2.isEmpty()) {
            int v1 = q1.remove();
            int v2 = q2.remove();
            if (v1 != v2) identical = false;
            
            tmp1.insert(v1);
            tmp2.insert(v2);
        }

        if (!q1.isEmpty() || !q2.isEmpty()) identical = false;

        while (!tmp1.isEmpty()) q1.insert(tmp1.remove());
        while (!tmp2.isEmpty()) q2.insert(tmp2.remove());

        return identical;
    }

    // לכתוב פעולה המקבלת תור של מספרים שלמים ומספר שלם, ומחזירה את כמות הפעמים שהמספר מופיע בתור
    public static int countOccurrences(Queue<Integer> q, int x) {
        Queue<Integer> temp = new Queue<Integer>();
        int count = 0;

        // מעבר על התור
        while (!q.isEmpty()) {
            int current = q.remove();
            if (current == x) {
                count++;
            }
            temp.insert(current);
        }

        // החזרת המצב לקדמותו
        while (!temp.isEmpty()) {
            q.insert(temp.remove());
        }

        return count;
    }

    public static void secret3(Queue<Integer> q) {
        if (q.isEmpty()) return;

        Queue<Integer> temp = new Queue<Integer>();
        int lastVal = q.remove();
        temp.insert(lastVal);

        while (!q.isEmpty()) {
            int current = q.remove();
            if (current != lastVal) {
                temp.insert(current);
                lastVal = current;
            }
        }

        while (!temp.isEmpty()) {
            q.insert(temp.remove());
        }
    }

    // פעולה המסדרת זוגיים בהתחלה ואי-זוגיים בסוף
    public static void separateEvenOdd(Queue<Integer> q) {
        Queue<Integer> evens = new Queue<Integer>();
        Queue<Integer> odds = new Queue<Integer>();

        // פיצול התור לשני תורי עזר לפי סוג המספר
        while (!q.isEmpty()) {
            int current = q.remove();
            if (current % 2 == 0) {
                evens.insert(current);
            } else {
                odds.insert(current);
            }
        }

        // הכנסה מחדש לתור המקורי - קודם כל הזוגיים
        while (!evens.isEmpty()) {
            q.insert(evens.remove());
        }

        // לאחר מכן האי-זוגיים
        while (!odds.isEmpty()) {
            q.insert(odds.remove());
        }
    }

    //.         1
    //.     5       3
    //.   4  1        1

    // פעולה הסופרת כמה עלים יש  בעץ
    public static int countLeaves(BinTreeNode<Integer> root) {
        if (root == null) {
            return 0;
        }
        // בדיקה האם הצומת הנוכחי הוא עלה
        if (root.getLeft() == null && root.getRight() == null) {
            return 1;
        }
        
        // אם הוא לא עלה, הסכום הוא מספר העלים משמאל + מספר העלים מימין
        return countLeaves(root.getLeft()) + countLeaves(root.getRight());
    } 
 


    // פעולה הבודקת האם ערך קיים בעץ
    public static boolean isExists(BinTreeNode<Integer> root, int x) {
        // תנאי עצירה 1: הגענו לסוף המסלול והערך לא נמצא
        if (root == null) {
            return false;
        }
        
        // תנאי עצירה 2: מצאנו את הערך בצומת הנוכחי
        if (root.getValue() == x) {
            return true;
        }
        
        // חיפוש בתתי העצים - מספיק שיחזור אמת מאחד הצדדים (שימוש ב-או)
        return isExists(root.getLeft(), x) || isExists(root.getRight(), x);
    }
     //.        1
    //.     5       3
    //.   4  1        1
    //  isExists(root, 3) -> true
    // return true


    public static boolean isMagic (Queue<Integer> q, int m){
        boolean magic = false;
        Queue<Integer> qTemp = new Queue<Integer>();
        int x = -1, y = -1, z = -1;

        if (m == 1) return false;

        // --- m-1 העברת איברים ראשונים לתור העזר ---
        int count = 1;
        while (!q.isEmpty() && count < m) // עפ"י נתוני השאלה אפשר בלולאת for
        {
            x = q.remove(); // בסיום הלולאה x מכיל את השכן משמאל
            qTemp.insert(x);
            count ++;
        }

        if (q.isEmpty())
        {
            magic = false; // מיקומו של האיבר האחרון בתור הוא m
        }
        else
        {
            y = q.remove(); // האיבר האמצעי
            qTemp.insert(y);

            if (!q.isEmpty())
            {
                z = q.remove(); // השכן הימני
                qTemp.insert(z);
                if (y == x + z) magic = true;
            }
        }

        // --- העברת שאר האיברים לתור העזר ---
        while (!q.isEmpty())
        {
            qTemp.insert(q.remove());
        }

        // --- החזרת האיברים לתור ---
        while (!qTemp.isEmpty())
        {
            q.insert(qTemp.remove());
        }

        return magic;
    }

    // --- פעולה המקבלת תור של מספרים שלמים ומספר שלם n בתחום גודל התור ---
    // --- ומחזירה אמת אם כל האיברים במקומות שהם כפולה של n הם איברי קסם ושקר אחרת ---
    public static boolean nMagic (Queue<Integer> q, int n){
        int k = size(q);
        for (int i = n ; i < k ; i += n)
        {   
            if (!isMagic(q, i))
                return false;
        }
        return true;
    }

    public static int size(Queue<Integer> q) {
        Queue<Integer> temp = new Queue<Integer>();
        int count = 0;

        while (!q.isEmpty()) {
            int current = q.remove();
            count++;
            temp.insert(current);
        }

        while (!temp.isEmpty()) {   
            q.insert(temp.remove());
        }

        return count;
    }

    // --- הוספת מטופל חדש לפי סדר הקדימויות ---
    public void priorityInsert(Patient p){
        Queue<Patient> qTemp = new Queue<Patient>();

        // --- העברת המטופלים בעלי קדימות גבוהה יותר או זהה לתור העזר ---
        while (!this.que.isEmpty() && this.que.head().getPriority() >= p.getPriority())
        {
            qTemp.insert(this.que.remove());
        }

        // --- הכנסת המטופל החדש ---
        qTemp.insert(p);

        // --- העברת שאר המטופלים לתור העזר ---
        while (!this.que.isEmpty())
        {
            qTemp.insert(this.que.remove());
        }

        // --- החזרת האיברים בחזרה לתור ---
        while (!qTemp.isEmpty())
        {
            this.que.insert(qTemp.remove());
        }
    }

    // --- פעולה המקבלת מס' זיהוי של מטופל ומידת הדחיפות החדשה שלו ---
    // --- המעדכנת את מידת הדחיפות של חולה ואת מיקומו בתור המטופלים ---
    public void update(int id, int pri){
        // --- שלב 1: איתור המטופל ---
        Queue<Patient> qTemp = new Queue<Patient>();
        while (!this.que.isEmpty() && this.que.head().getId() != id)
        {
            qTemp.insert(this.que.remove());
        }

        // --- הוצאת המטופל מהתור ---
        Patient p = que.remove();

        // --- העברת שאר המטופלים לתור העזר ---
        while (!this.que.isEmpty())
        {
            qTemp.insert(this.que.remove());
        }

        // --- החזרת האיברים בחזרה לתור ---
        while (!qTemp.isEmpty())
        {
            this.que.insert(qTemp.remove());
        }

        // --- שלב 2: עדכון הדחיפות של המטופל והחזרתו לתור למקום המתאים ---
        p.setPriority(pri);        // עדכון המטופל
        this.priorityInsert(p);    // החזרת המטופל לתור
    }



    
    // lst -> 1 -> 3 -> 5 -> 3 -> 1 -> 9 -> 4 -> null
    // Q3 - lists
    public static Node<Integer> what (Node<Integer> lst, int x){
        if (lst == null)
            return null;

        Node<Integer> temp = what (lst.getNext(), x);

        if (lst.getValue() == x)
            return temp;

        lst.setNext(temp);
        return lst;
    }

    public static void Guess (Node<Integer> lst){
        if (lst != null) {
            Node<Integer> temp = what(lst.getNext(), lst.getValue());
            lst.setNext (temp);
            Guess (lst.getNext());
        }
    }

 
    public static int Sod1(int x){
        if(x<10)
            return x;
        return Sod1(x/10);
    }

    // Sod1(123)

    // what is the purpose of the function Sod1?



    public static boolean Sod1 (int[] arr, int x, int i){
        if ( i == -1 ) return false;
        
        if (arr[i] == x) return true;
        
        return Sod1(arr, x, i - 1);
    }

    public static boolean Sod2 (int[] arr, int x, int i){
        if (i == 0) return false;
        
        if (Sod1(arr, x - arr[i], i - 1)) return true;
        
        return Sod2(arr, x, i - 1);
    }


    public static void main(String[] args) {
        Queue<Integer> q1 = new Queue<>();
        q1.insert(1); q1.insert(2); q1.insert(3);
        
        System.out.println("Sum: " + sumQueue(q1));
        System.out.println("Exists 2? " + secret(q1, 2));
        
        Queue<Integer> q2 = new Queue<>();
        q2.insert(1); q2.insert(2); q2.insert(3);
        System.out.println("Secret 2? " + secret2(q1, q2));
        q1.insert(2);
        System.out.println("Occurrences of 2: " + countOccurrences(q1, 2));
        System.out.println("Before separateEvenOdd: " + q1);
        separateEvenOdd(q1);
        System.out.println("After separateEvenOdd: " + q1);

        BinTreeNode<Integer> root = new BinTreeNode<>(1);
        //     1
        //null   null
        root.setLeft(new BinTreeNode<>(2));
        //     1
        //  2   null
        root.setRight(new BinTreeNode<>(3));
        //      1
        //   2     3
        root.getLeft().setLeft(new BinTreeNode<>(4));
        //      1
        //   2     3
        // 4   null
        root.getLeft().setValue(5);
        //      1
        //   5     3
        // 4   null
        
        System.out.println("Count Leaves: " + countLeaves(root));
        System.out.println("Is 2 exists? " + isExists(root, 2));
        System.out.println("Is 4 exists? " + isExists(root, 4));
    }
}


// 1 2 3 4 5 6
// q, 4

//  m - 1 = 3.    1 ; i < m ; i++

// 4 5 6
// val1 = 3

// m - 2 = 2.      1 ; i < m-1 ; i++
// 3 4 5 6
// val1 = 2
// val1 = q.remove() -> 3

public static boolean checkTreeForNum (BinTreeNode<Integer> root, int x) {
    if (root == null) {
        return false;
    }
    if (root.getValue() == x) {
        return true;
    }
    return checkTreeForNum(root.getLeft(), x) || checkTreeForNum(root.getRight(), x);
}

public static int secret(BinTreeNode<Integer> root, int x){
    if(!checkTreeForNum(root, x))
        return 0;
    if((root.getLeft() != null && root.getLeft().getValue() == x) || (root.getRight() != null && root.getRight().getValue() == x))
        return root.getValue() + secret(root.getLeft(), x) + secret(root.getRight(), x);
    return secret(root.getLeft(), x) + secret(root.getRight(), x);
}



public static int what(int[] arr){
    int y = 0;
    int x = arr[0];

    for (int i = 0; i < arr.length; i++){
        y += arr[i];
        x = Math.max(x, y);
        y = Math.max(y, 0);
    }

    return x;
}

// arr = -4, 8, -1, 3, -10, 7, -2, 5
// y = 10, x = 10, i = 7
// x = 10 



public static boolean mmm(Queue<Integer> q, int z)
{
    q.insert(0);
    int num = q.head();
    int y = 0;

    while (q.head() > 0)
    {
        if (y < z)
        {
            if (q.head() > num)
            {
                y++;
            }
            else
            {
                y = 1;
            }

            num = q.head();
        }

        q.insert(q.remove());
    }

    q.remove();
    return y == z;
}

// mmm(q,4)
// q = 1, 4, 2, 3, 5, 7, 6

// num = 1, q.head = 4
// num = 4, q. head = 2
// num = 2, q.head = 3

// z = 4
// q = 1, 4, 2, 3, 5, 7, 6
// num = 7
// y = 4

// 3 + n * 7 + 2 = 5 + 7n = O(n)

public static int what(Queue<Integer> q, int n){
    if (mmm(q,n))
        return n;
    return what(q, n-1);
}

// (O(n) + 1)*n = O(n)^2.  O(n) + O(n) = 2O(n) = O(n)

// q = 1, 4, 2, 3, 5, 7, 6

//what 4

// q = 1, 4, 2, 3, 5, 7, 6
// n = 7
// mmm(q, n) = false
// return 4

// q = 1, 4, 2, 3, 5, 7, 6
// n = 6
// mmm(q, n) = false
// return 4

// q = 1, 4, 2, 3, 5, 7, 6
// n = 5
// mmm(q, n) = false
// return 4

// q = 1, 4, 2, 3, 5, 7, 6
// n = 4
// mmm(q, n) = true
// return 4

public static int size(Queue<Integer> q){
    int counter = 0;
    Queue<Integer> temp = new Queue<>();
    while(!q.isEmpty()){
        temp.insert(q.remove());
        counter++;
    }
    while(!temp.isEmpty()){
        q.insert(temp.remove());
    }
    return counter;
}
public static boolean isMMatch(Queue<Integer> q1, Queue<Integer> q2, int m){
    Queue<Integer> temp1 = new Queue<>();
    Queue<Integer> temp2 = new Queue<>();
    boolean flag = true;
    for(int i = 0; i < size(temp1) - m; i++){
        temp1.insert(q1.remove());
    }
    for(int j = 0; j < size(temp2) - m; j++){
        temp2.insert(q2.remove());
    }
    for(int k = 0; k < m; k++){
        if (q1.head() != q2.head())
            flag = false;
    }
    while(!temp1.isEmpty()){
        q1.insert(temp1.remove());
    }
    while(!temp2.isEmpty()){
        q2.insert(temp2.remove());
    }
    return flag;
}

public static int maxMatch(Queue<Integer> q1, Queue<Integer> q2){
    int m = 1;
    while(isMMatch(q1, q2, m)){
        m++;
    }
    m--;
    return m;
}
// 1, null, null ... , null
// amount = 1
// 85, 88, 12, 1, 4, 11, null, null, null, null
// amount = 6
public boolean isStopping(int n){
    for(int i = 0; i < this.amount; i++){
        if (n == this.arr[i])
            return true;
    }
    return false;
}

public static Node<Integer> cityLines(BusStaion[] arr){
    Node<Integer> node = new Node<Integer>(arr[0].getArr[0]);
    Node<Integer> lst = node;
    Node<Integer> tmp = node;
    boolean flag = false;
    for(int i = 0; i<arr.length(); i++){
        for(int j = 0; j<arr[i].getAmount(); i++){
            while(tmp != null){
                if(tmp.getValue() == arr[i].getArr[j]){
                    flag = true;
                    break;
                }
            }
            if(flag == false){ // 3 -> 5 -> 6 -> 7 -> 12 -> null
                while(tmp.getNext()!=null){
                    tmp = tmp.getNext();
                }
                tmp.setNext(arr[i].getArr[j]);
            }
            tmp = lst;
        }
    }
    return lst;
}

public static int[] filter(int[] arr, int num){
    int counter = 0, k = 0;
    for(int i = 0; i < arr.length; i++){
        if(arr[i] == num)
            counter++;
    }
    int[] newArr = new int[arr.length - counter];
    for(int j = 0; j < arr.length; j++){
        if(arr[j] != num){
            newArr[k] = arr[j]; 
            k++;
        }    
    }
    return newArr;
}

public ReportCard(String name, int num){
    this.stuName = name;
    this.subArray = new Subject[num];
}

Queue<String> q = new Queue<>();
Node<Queue<String>> NQ = new Node<>(q);


public static Node<String> uniqueStrings(Node<Queue<String>> lq){
    Node<Queue<String>> tempLQ = lq;
    Queue<String> tempQ = new Queue<String>();
    Node<String> returnedLst = new Node<String>(null);
    Node<String> temp = returnedLst;
    String val;
    while(tempLQ != null){
        while(!tempLQ.getValue().isEmpty()){
            val = tempLQ.getValue().head();
            tempQ.insert(tempLQ.getValue().remove());
            while(temp != null){
                if(val == temp.getValue()){
                    //////////////
                }
                else{
                    // run while until the end and connect
                    temp.setNext(new Node<String>(val));
                }
            }
        }
        while(!tempQ.isEmpty()){
            tempLQ.getValue().insert(tempQ.remove());
        }
    }
}


public static Node<Integer> conseqNum(Node<Integer> lst, int num){
    int sum = 0;
    Node<Integer> tmp1 = lst;
    Node<Integer> tmp2 = lst;
    while(tmp1 != null){
        while(sum < num && tmp2 != null){
            sum += tmp2.getValue();
            if(sum == num)
                return tmp1;
            tmp2 = tmp2.getNext();
        }
        tmp1 = tmp1.getNext();
        tmp2 = tmp1;
        sum = 0;
    }
    return null;
}


public static int sumNumLists(Queue<Node<Integer>> ql, int num){
    if(ql.isEmpty())
        return 0;
    if(conseqNum(ql.remove(), num) != null)
        return sumNumLists(ql, num) + 1;
}

public static Student longestWaitingStudent(Queue<Student> studentQ){
    Queue<Student> tempQ = new Queue<>();
    int maxWaitTime = Integer.MIN_VALUE;
    Student returnedStudent;
    while(!studentQ.isEmpty()){
        if(studentQ.head().getWaitingTime() > maxWaitTime){
            maxWaitTime = studentQ.head().getWaitingTime();
        } 
        tempQ.insert(studentQ.remove());
    }
    while(!tempQ.isEmpty()){
        if(tempQ.head().getWaitingTime == maxWaitTime){
            returnedStudent = new Student(tempQ.remove());
            break;
        }
        studentQ.insert(tempQ.remove());
    }
    while(!tempQ.isEmpty){
        studentQ.insert(tempQ.remove());
    }
    return returnedStudent;
}

public static Queue<Student> secondQuestion (Queue<Student> studentQ){

}


while(!studentQ.isEmpty())
    if(studentQ.head().isHonors())
        tempHonors.insert(studentQ.remove())
    else
        tempNonHonors.insert(studentQ.remove())

while(!tempHonors.isEmpty())
sortedQ.insert(longestWaitingStudent(tempHonors));

while(!tempNonHonors.isEmpty())
sortedQ.insert(longestWaitingStudent(tempNonHonors));

return sortedQ;


public static Node<Integer> conseqSum (Node<Integer> lst, int num){
    int sum = 0;
    Node<Integer> tmp1 = lst;
    Node<Integer> tmp2 = lst;
    while(tmp1 != null){
        while(sum < num && tmp2 != null){
            sum += tmp2.getValue();
            if(sum == num)
                return tmp1;
            tmp2 = tmp2.getNext();
        }
        tmp1 = tmp1.getNext();
        tmp2 = tmp1;
        sum = 0;
    }
    return null;
}

public static int countConseqSum(Queue<Node<Integer>> ql, int num){
    if(ql.isEmpty())
        return 0;
    if(conseqSum(ql.remove(), num) != null)
        return countConseqSum(ql, num) + 1;
    return countConseqSum(ql, num);
}

public static void fun2 (BinNode<Integer>bt,Queue<Integer>q, int n)
{
     if (bt != null){
        if (n % 2 == 0)
            q.insert(bt.getValue());
        fun2 (bt.getLeft(), q, n+1);
        fun2 (bt.getRight(), q, n+1);
    }    	
}
public static boolean fun1 (BinNode<Integer> bt)
{
   Queue<Integer> q=new Queue<Integer>();
   fun2 (bt, q, 0);
   return fun3 (q);
}

public static boolean fun3 (Queue<Integer> q)
{
while (!q.isEmpty())
{
  	int x = q.remove();
  	if (! q.isEmpty() && x >= q.head())
       		return false;
}
            return true;
}
// fun1(tr)

//.                 3
//               6     2
//            4.   6.    1
//                9 7.
//                  
// q = 3 -> 4 -> 7 -> 2 -> 1
//
// fun3 - > O(n)
// fun2 - > O(n) - 
// fun1 - > O(n) + O(n) = O(n)

public static boolean posOrder(int[] arr){
    int previous = 0;
    for(int i = 0; i < arr.length; i++){
        if(arr[i] < previous && arr[i] > 0)
            return false;
        if(arr[i] > 0)
            previous = arr[i];
    }
    return true;
}

public static int discount (int age, int kids, boolean city){
    int discountCode;
    if (age > 70)
        discountCode = 100;
    else
        discountCode = 200;

    if (kids >= 3)
        discountCode = discountCode + 10;
    else
        discountCode = discountCode + 20;

    if (city)
        discountCode = discountCode + 1;
    else
        discountCode = discountCode + 2;

    return discountCode;
}


public static int[] discountCounter (int[] arr){
    int[] returnedArr = new int[3];
    int num1, num2, num3;
    for (int i = 0; i < returnedArr.length; i++){
        returnedArr[i] = 0;
    }

    for (int j = 0; j < arr.length; j++){
        num1 = arr[j] % 10;
        num2 = (arr[j] / 10) % 10;
        num3 = arr[j] / 100;
        if(num1 == 1)
            returnedArr[2]++;
        if(num2 == 1)
            returnedArr[1]++;
        if(num3 == 1)
            returnedArr[0]++;
    }

    return returnedArr;
}

public static Queue<Room> distribute (Queue<User> q, int x){
    int numOfRooms = q.size()/x;
    if (q.size()%x > 0)
        numOfRooms++;
    Queue<Room> roomQ = new Queue<>();
    for(int i = 0; i < numOfRooms; i++){
        Room tempRoom = new Room(i+1);
        for(int j = 0; j < x; j++){
            if(!q.isEmpty)
                tempRoom(addUser(q.remove));
        }
        roomQ.insert(tempRoom);
    }
    return roomQ;
}

public static int secret(Queue<Integer> q){
    if (q.isEmpty())
        return -1;
    int x = q.remove();
    if (q.isEmpty())
        return x;
    int y = secret(q);
    q.insert(x);
    return y;
}

public static boolean mystery(Queue<Integer> q, int c){
    if (c == 0 || q.isEmpty())
        return true;
    int x = q.remove();
    if (q.isEmpty())
        return false;
    int y = secret(q);
    if (x == y)
        return mystery(q, c - 1);
    return false;
}


// q1: 6 -> 3 -> 2
// y = 1
// secret: returns the end of queue, and reverses it

// q2: 2 -> 4 -> 7 -> 2
// (5 + n + 1)n = 5n + n^2 + n = 6n + n^2 = O(n^2)


// acc. acac. abbbbcbbbbbbbabbbcaaaa
// abcabcabcabacbacbacbacabcabcacc.  acc accaccaccbac
// aabcabcabcabcabcabcabcc abbacc
// a^2 b^2 c^2   {aa,bb,cc,dd}
// aa bb cc
// the word abcc E L1
// but abcc E L2 because it doesn't have acc
// s1 = ab, s2 = cd => s1 + s2 = abcd
// L = ab, bc, ca
// R(L) = ba, cb, ac
    

// L3 = {aWc | that W E {a,b,c}*}
// R(L3) = {cWa | """"""""}

// L3 * R(L3) = {a(W1)cc(W2)a | W1 E {a,b,c}* , W2 E {a,b,c}*}
// {a^n} cut {b^n} = {empty} 






// L4 = {a^(n/2)b^(m+2)c^n | m>=0, n>0, n is even}
// R(L5) = {a^n b^m w | w E {a,c}* , m,n>=0}



public void insertNum(int x){
    Node<NumCount> temp = this.lst;
    if(temp.getValue().getNum() > x){
        Node<NumCount> newNode = new Node<>(new NumCount(x, 1));
        newNode.setNext(temp);
        this.lst = newNode;
    }
    else if(temp.getValue().getNum() == x){
        temp.getValue().getCount()++;
    }
    while(temp.getNext() != null){
        if(temp.getNext().getValue().getNum() > x){
            Node<NumCount> newNode = new Node<>(new NumCount(x, 1));
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
            break;
        }
        else if(temp.getNext().getValue().getNum() == x){
            temp.getNext().getValue().getCount()++;
            break;
        }
        temp = temp.getNext();
    }
    if(temp.getNext() == null){
        temp.setNext(new Node<>(new NumCount(x, 1)));
    }
}

public void priorityInsert(Patient p){
    Queue<Patient> temp = new Queue<>();
    while(!q.isEmpty() && q.head().getPriority >= p.getPriority){
        temp.insert(q.remove());
    }
    temp.insert(p);

    while(!q.isEmpty()){
        temp.insert(q.remove());
    }

    while(!temp.isEmpty()){
        q.insert(temp.remove());
    }
}

public void update (int id, int pri){
    Queue<Patient> temp = new Queue<>();
    while(!q.isEmpty() && q.head().getId != id){
        temp.insert(q.remove());
    }
    Patient newPriPatient = q.remove();
    newPriPatient.setPriority(pri);
    while(!q.isEmpty()){
        temp.insert(q.remove());
    }
    while(!temp.isEmpty()){
        q.insert(temp.remove());
    }
    q.priorityInsert(newPriPatient);
}

public static int sod1(Queue<Integer> q)
{
    int i = q.remove();
    int result = i;

    if (!q.isEmpty())
    {
        int j = sod1(q);
        if (result > j)
            result = j;
    }

    q.insert(i);
    return result;
}


// myQueue : 2 -> 31 -> 4 -> 17 -> 5
// 2

// sod1: input -> תור
// output -> הופך את התור ומחזיר את הכי קטן בתור 



/**
 * The function receives a number greater than or equal to 0
 * and returns...
 */
public static int sod2(int i)
{
    if (i == 0)
        return 0;

    int a = i % 10;
    int b = sod2(i / 10);

    if (a > b)
        return a;

    return b;
}

public static boolean rankedQueue (Queue<Integer> q){
    Queue<Integer> temp = new Queue<>();
    int counterPrevious = 0, counterNow;
    boolean flag = true;
    while(!q.isEmpty()){
        counterNow = 0;
        int currentHead = q.head();
        while(q.head() == currentHead){
            counterNow++;
            temp.insert(q.remove());
            if(q.isEmpty())
                break;
        }
        if (counterNow <= counterPrevious){
            flag = false;
        }
        counterPrevious = counterNow;
    }
    while(!temp.isEmpty()){
        q.insert(temp.remove());
    }
    return flag;
}

public static boolean isArranged(Node<Integer> lst){
    Node<Integer> pos = lst;
    int counter = 0;
    int max = Integer.MIN_VALUE;
    while(pos != null){
        counter++;       
    }
    pos = lst;
    if(counter % 2 != 0)
        return false;

    for(int i = 0; i < counter/2; i++){
        if (max < pos.getValue())
            max = pos.getValue();
        pos = pos.getNext();
    }
    for(int j = 0; j < counter/2; j++){
        if(max >= pos.getValue())
            return false;
        pos = pos.getNext();
    }
    return true;
}

public static boolean isIncluded(Node<Integer> lst1, Node<Range> lst2){
    Node<Integer> temp1 = lst1;
    Node<Range> temp2 = lst2;
    while(temp1 != null){
        while(!(temp1.getValue() >= temp2.getValue().getLow() && temp1.getValue() <= temp2.getValue().getHigh())){
            temp2 = temp2.getNext();
            if(temp2 == null)
                return false;
        }
        temp1 = temp1.getNext();
    }
    return true;
} 
// 1*n + 1*1 + 1*1 + 1*1 ... (example in case that the first node run over n ranges)
// n + 1*(n-1) = n + n-1 = 2n -1 = O(n)

public static int mostSick(Queue<CovidTest> q){
    Queue<CovidTest> temp = new Queue<CovidTest>();
    Queue<covidCount> cityCounterQ = new Queue<covidCount>();
    int x, maxCount = 0;
    int count = 0;
    while(!q.isEmpty && !temp.isEmpty){
        x = q.head().getCityCode();
        while(!q.isEmpty){
            if(q.head().getSick() == true && q.head().getCityCode == x){
                count++;
                q.remove();
            }
            else if(q.head().getSick == false)
                q.remove();
            else{
                temp.insert(q.remove());
            }
        }
        if(count > maxCount)
            maxCount = count;
        cityCounterQ.insert(new cityCount(count, x));
        
        x = temp.head().getCityCode();
        count = 0;
        while(!temp.isEmpty){
            if(temp.head().getSick() == true && temp.head().getCityCode == x){
                count++;
                temp.remove();
            }
            else
                q.insert(temp.remove());
        }
        if(count > maxCount)
            maxCount = count;
        cityCounterQ.insert(new cityCount(count, x));   
    }
    while(!cityCounterQ.isEmpty()){
        if(cityCounterQ.head().getCount == maxCount)
            return cityCounterQ.head().getCityCode;
        cityCounterQ.remove();
    }
}

// EDEN - TRY TO PUT THIS CODE IN YOUR PAPER
public static int biggestSum(int[] arr){
    int maxSum = 0;
    int currentSum = 0;
    for(int i = 0; i < arr.length; i++){
        if(arr[i] == 0)
            i++;
        while(arr[i] != 0){
            currentSum = currentSum + arr[i];
            i++;
            if(i == arr.length)
                return maxSum;
        }
        if (maxSum < currentSum)
            maxSum = currentSum;
    }
    return maxSum;
}


public static boolean posOrder(int[] arr){
    int previousNum = 0;
    for(int i = 0; i < arr.length; i++){
        if(arr[i] > 0 && arr[i] < previousNum){
            return false;
        }
        else if(arr[i] > 0)
            previousNum = arr[i];
    }
    return true;
}

public static boolean isPrefix(Node<Integer> lst1, Node<Integer> lst2){
    Node<Integer> tmp1 = lst1;
    Node<Integer> tmp2 = lst2;
    while(tmp1 != null){
        if(tmp1.getValue() != tmp2.getValue())
            return false;

        tmp1 = tmp1.getNext();
        tmp2 = tmp2.getNext();

        if(tmp2 == null && tmp1 != null)
            return false;
    }
    return true;
}

}