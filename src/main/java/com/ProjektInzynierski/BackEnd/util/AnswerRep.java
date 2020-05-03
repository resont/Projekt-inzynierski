package com.ProjektInzynierski.BackEnd.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnswerRep implements Container{

    public List<String> answers;

    public void setAnswers(List<String> Answers){
        answers = Answers;
    }

    @Override
    public Iterator getIterator() {
        return new AnswerIterator();
    }

    private class AnswerIterator implements Iterator{

        int index;

        @Override
        public boolean hasNext() {
            return index < answers.size();
        }

        @Override
        public Object next() {
            if(this.hasNext()){
                return answers.get(index++);
            }
            return null;
        }
    }
}
