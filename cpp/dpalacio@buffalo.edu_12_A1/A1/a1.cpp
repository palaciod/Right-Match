// File: a1.cpp
//AUthor: Daniel Palacio dpalacio
#include<iostream>
#include<fstream>
#include<unordered_map>
#include<string>
#include<map>
#include<set>

using namespace std;

int main(int argc, char const *argv[]) {
  ifstream dna_Seq(argv[1]);
  string line;
  string newLine;
  string noN;


  if(dna_Seq.is_open()){
    unsigned long int ia;

    long int ib;
    multimap<string,int> _map;
    map<string,int> _final;
    map<string,int>::iterator it;
    multiset<string> set;
    multiset<string>::iterator _it;
    int countLines;
    countLines=-1;
    getline(dna_Seq,line);



 string intFront;
    string intBack;

    for(unsigned int i=0;i<line.length();i++){
      intFront+=line[i];
      if(line[i]==' '){break;}
    }
    for(unsigned int i = 2;i<line.length();i++){
      intBack+=line[i];
    }
    int valFront = atoi(intFront.c_str());
    int valBack = atoi(intBack.c_str());
    ia = valFront;

    ib = valBack;
    while(!line.empty()){
      getline(dna_Seq,line);
      countLines++;
      newLine+=line;

    }

    for(unsigned int i = 0;i<newLine.length();i++){

      if(newLine[i]=='N'){


        //std::cout << noN << std::endl;
      }else{noN+=newLine[i];}
    }





    for(unsigned int i =0;i<noN.length();i++){

      if(countLines<ib){std::cout << "error" << std::endl;break;}

      if(ib<=1){std::cout << "error" << std::endl;break;}
      if(ia<3){std::cout << "error" << std::endl;break;}
      if(ia>10){std::cout << "error" << std::endl;break;}

      //std::cout << resultLine.find("N") << std::endl;






    }


    // for(_it = _s.begin();_it != set.end();_it++){
    //   std::cout << *_it << _set.count(*_it) << std::endl;
    // }

  }

    return 0;
  }
