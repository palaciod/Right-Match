/***
 *  File: a2.hpp
 *  Author: Daniel Palacio <dpalacio@buffalo.edu>
 */

#ifndef A2_HPP
#define A2_HPP

// MAKE SURE TO UPDATE YOUR INFORMATION IN THE HEADER OF THIS FILE
// IMPLEMENT MISSING FUNCTIONALITY OF sorted_sc_array IN THIS FILE
// YOU ARE NOT ALLOWED TO INCLUDE ANY OTHER HEADERS EXCEPT OF <algorithm>
// YOU CAN CHANGE/EDIT ANY CODE IN THIS FILE AS LONG AS SEMANTICS IS UNCHANGED
// AND MATCHES SPECIFICATION AS PROVIDED IN THE ASSIGNMENT TEXT

#include <algorithm>

using namespace std;
class sorted_sc_array {
public:
    sorted_sc_array() : size_(0), ptr_(nullptr) { }

    ~sorted_sc_array() { delete[] ptr_; }

    // IMPLEMENT ME
    sorted_sc_array(const sorted_sc_array& A){

    size_ = A.size_;
    cap = A.cap;
    ptr_ = new signed char [cap];
    copy(A.ptr_,A.ptr_+size_,ptr_);
    }

    // IMPLEMENT ME
    sorted_sc_array& operator=(const sorted_sc_array& A){
      if(this == &A){
        //delete[] ptr_;
        return *this;
      }
        delete[] ptr_;
       size_ = A.size_;
       cap = A.cap;

     if(size_!=0){
       ptr_ = new signed char[cap];
       copy(A.ptr_,A.ptr_+size_,ptr_);
     }
     return *this;
    }

    // RETURNS SIZE OF THE ARRAY (i.e. HOW MANY ELEMENTS IT STORES)
    int size() const { return size_; }

    // RETURNS RAW POINTER TO THE ACTUAL DATA, CAN BE INVOKED AT ANY TIME
    const signed char* data() const {
     sort(ptr_,ptr_+size_);
      return ptr_;
      delete[] ptr_;
    }


    // IMPLEMENT ME: AFTER INSERT COMPLETES THE ARRAY MUST BE IN ASCENDING ORDER
    void insert(signed char c){
      if(size_==0){ptr_ = new signed char[cap];}

    ptr_[size_]=c;
    size_++;
    if(size_==cap-1){
      cap=cap*2;
      signed char* temp = new signed char[cap];
      copy(ptr_,ptr_+size_,temp);
      delete[] ptr_;
      ptr_=temp;
      //delete[] temp;
    }



    }


private:
    int cap=4096/ sizeof ptr_;
    int size_;         // size of the array
    signed char* ptr_; // pointer to the array

}; // class sorted_sc_array

#endif // A2_HPP
