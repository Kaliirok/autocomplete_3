import java.util.ArrayList;
import java.util.List;

/**
 * TODO: implement this class as described in README.md and AutoCompleteInterface.java.
 *
 * This file is the only one you submit. Design your own node class and
 * internal structure — nothing about it is given to you beyond what
 * AutoCompleteInterface.java requires.
 */

public class AutoComplete implements AutoCompleteInterface {
    private DLBNode root;
    private String userInput;
    private DLBNode curr;
    private DLBNode currprev;
    private char sep;
    
    public AutoComplete(char separator) {
        // TODO
        curr = root;
        userInput = "";
        sep=separator;

    }

    @Override
    //Taken from the DLB lab
    //should be working maybe
    public boolean add(String path) {
        boolean newadd= false;
        String key= path;
        if (key == null){
            throw new IllegalArgumentException("calls put() with a null key");
        } 
        if (key.length() == 0){
            throw new IllegalArgumentException();//cause I guess it can add nothing
        } 
        if (root == null) {
             root = new DLBNode(key.charAt(0), 0);
        }
        DLBNode scout = root;
        //We need to see if the child of the things is the right answer
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            //scout =scout.child;
            if (scout.child!= null) {
                scout= scout.child;
                //We need to go down the list of siblings
                while (scout.sibling != null) {
                    if (scout.letter == c) {
                        break;
                    }
                    scout = scout.sibling;
                }
                if(scout!=null && scout.letter==c){
                    //so it wass the last sibling 
                }
                if(scout.letter != c) {//c not found
                    newadd=true;
                    scout.sibling = new DLBNode(c, i);
                    scout = scout.sibling;
                }
            } else if (scout.child ==null){ //so the node doesn;t have any child and you need to make one
                    newadd=true;
                    scout.child = new DLBNode (c, i+1);
                    scout= scout.child;
            }
        }
        //so if something was added the newadd would change to true and it would return true
        if(newadd==true){
            scout.isWord = true;
            return true;
        }
        return false;
}

    @Override
    //There is also a note to do this second soooo idk 
    //So this just advances down or across the list??
    public boolean advance(char c) {
        // TODO
        boolean answer=false;
        
        if(curr==null && userInput.isEmpty()){
            currprev=curr; //so this should stay as curr
            curr=root;
        }
        if(curr==null && !userInput.isEmpty()){
            //there is a problem
            return false;
        }
        //so same traveseral as add??
        if(curr.child!=null){
            currprev=curr; //so this should stay as curr
            if(curr.child.letter==c){ //so we found it 
                answer=true;
                userInput= userInput+c;
                curr=curr.child;
            }else if(curr.child.letter!=c){
                curr=curr.child;
                while(curr.sibling !=null){
                    if(curr.sibling.letter == c){
                        //found it 
                        //currprev=curr; //so this should stay as curr
                        answer=true;
                        userInput= userInput +c;
                        curr=curr.sibling;
                        break;
                    }else{
                        curr=curr.sibling;
                    }
                }
                //so we couldn't advance to their child so we check the child sibling

            }
        }else{
            //It doesn't exist return false
            return false;
        }
        //We need to see if the child of the things is the right answer
         
        //userInput= userInput+c;
        return answer;
    }

    @Override
    //Okay list says this next
    //It's like advance but backwards
    public void retreat() {
        // TODO
        // moves the running prefix back by one character. 
        //So this needs to change curr and the string oh hmmm maybe it would be better to have it as a different data type
        //How do I go back up the tree?
        if(userInput.length()>=1){
              userInput = userInput.substring(0, userInput.length() - 1);
        }else{
            userInput="";
        }
      
        System.out.println(userInput);
        curr=currprev;

        //throw new UnsupportedOperationException("retreat() not implemented yet");
    }

    @Override
    //Okay note is for reset next and then retreat and then cry and then complete
    //Why is this not first love this one
    public void reset() {
        // TODO
        //clears the running prefix back to empty in one call.
        //So does this just set curr to root??
        curr= root;
        //Oh it needs to clear the thingy
        userInput= "";
       // throw new UnsupportedOperationException("reset() not implemented yet");
    }

    @Override
    //The last dreaded method evil bringer 
    public String complete() {
        // TODO
        //throw new UnsupportedOperationException("complete() not implemented yet");
        // complete()` asks a cheaper question than `nextPossibleChars()`, not
        // the same one.** `nextPossibleChars()` has to enumerate every child's
        // character. `complete()` never needs to know what the children are —
        // only whether there are zero, exactly one, or more than one.
        // extends the running prefix as far as it can go
        // unambiguously, stopping at a branch, a leaf, a complete path,
        // or right after appending the trie's separator character 
        // (the separator character is configured via the constructor).
        //Okay so I guess it returns the furtherest word soooo I never made that
        //Nevermind the lab did it for me 

        String answer="";
        if(curr!= null){
            int count=0; 
            //This checks childern
            if(curr.child!=null){
                DLBNode temp=curr.child;
                count++;
                while(temp.sibling!=null){
                    count++;
                    temp=temp.sibling;
                }
                //Okay option 1 the curr has more than one child
                if(count>1){
                    //so it doesn't know where to go
                    return answer;
                }

                //Option 2 nothing left so leaf
                //zero kids so check siblings?

                //Option 3 the current prefix is already a valid path so one or zero kids???
                //follow the path
                if(count<=1){
                    int count1=0;
                    if(curr.isWord==true){
                        return answer; //cause it should stop
                    }
                    while(count1<=1 && curr.child!=null){
                        count1=0;
                        currprev= curr;
                        curr=curr.child;
                        if(curr.letter== sep){
                            userInput=userInput+curr.letter;
                            answer=answer+sep;
                            return answer;
                        }
                        answer= answer + curr.letter;
                        userInput=userInput+curr.letter;
                       if(curr.isWord==true){
                            return answer;
                        }
                        if(curr.child!=null){
                            temp=curr.child;
                            count1++;
                        while(temp.sibling!=null){
                            count1++;
                            temp=temp.sibling;
                        }
                     //Okay option 1 the curr has more than one child
                        if(count1>1){
                        //so it doesn't know where to go
                            return answer;
                        }
                    }
                }
            }
            //Okay what are u supposed to be
            if(curr.letter == sep){
                    answer=answer+sep;
                    return answer;
            }
            
        }
        
    }
        return answer;

    }

    @Override
    //So there is a note to do this second
    //so I'm going to this one next one
    //works mostly I think
    public List<Character> nextPossibleChars() {
        // TODO
        //This is what you are supposed to do nextPossibleChars()` — 
        // reports what could come next, without changing
        //any state.
        //So it needs to look at the childern of the curr node
        //And it returns an array so 
        System.out.println("The problem is here!!!!"); //I justed reliazed that this doesn't work
        List<Character> possibleChars = new ArrayList<>();
        DLBNode temp= curr;
        if(temp!=null){
            if(temp.child!=null){
                //so now we go through the child and it's silbings and add them to the list
                temp=temp.child;
                possibleChars.add(temp.letter);
                while(temp.sibling!=null){
                    temp= temp.sibling;
                    possibleChars.add(temp.letter);
                }
            }
            return possibleChars;
        } else{
            temp=root;
            if(temp!=null){
                if(temp.child!=null){
                //so now we go through the child and it's silbings and add them to the list
                temp=temp.child;
                possibleChars.add(temp.letter);
                while(temp.sibling!=null){
                    temp= temp.sibling;
                    possibleChars.add(temp.letter);
                }
                }
            }
            return possibleChars;
        }
        //return null;
    }


    //Gotten from the lab
    private class DLBNode {
    private char letter;
    private boolean isWord;
    private DLBNode sibling;
    private DLBNode child;
    private int level; // for level-order traversal

    private DLBNode(char c, int level) {
      letter = c;
      this.level = level;
    }
  }
}
