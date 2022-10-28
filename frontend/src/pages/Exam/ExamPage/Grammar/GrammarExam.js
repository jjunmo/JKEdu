import styled from "styled-components";
import { produce } from "immer";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

import "App.css";

import exams from "db/exam_dummy/grammar.json";

const Main = styled.div`
  width: 800px;
  margin: 0 auto;
  font-family: SCDream5;
`;

const Question = styled.div`
  box-sizing: border-box;
  margin: 20px 0;
  font-size: 30px;
`;

const MainText = styled.div`
  box-sizing: border-box;
  font-size: 25px;
  margin: 10px 0;
  white-space: pre;
`;

const ImgDiv = styled.div`
  text-align: center;
  margin: 0 auto;
`;
const Img = styled.img`
  text-align: center;
  width: 250px;
  height: 250px;
`;

// 문제 보기 모음
const ExamplesDiv = styled.div``;

const Example = styled.div`
  box-sizing: border-box;
  width: 800px;
  height: 40px;
  font-size: 30px;
  margin-top: 20px;
  cursor: pointer;

  &:hover {
    border: 2px solid grey;
    border-radius: 10px;
    background-color: #ededed;
  }
`;

const ButtonDiv = styled.div`
  box-sizing: border-box;
  margin-top: 25px;
  text-align: center;
`;

const NextProblemButton = styled.button`
  box-sizing: border-box;
  width: 150px;
  padding: 10px 30px 10px 30px;
  text-align: center;
  border: none;
  border-radius: 15px;
  font-size: 15px;
  font-family: SCDream5;
  color: white;
  background-color: #1e1d4d;
  text-decoration: none;
  &:hover {
    cursor: pointer;
  }
`;

const GrammarExam = () => {
  const navi = useNavigate();

  const exam = exams.exam;

  // number : 몇 번째 문제
  // answer : 정답 (주관식이면 서술형 답, 객관식이면 보기를 저장)
  // answerNum : 몇 번째 보기인지
  const [examInfo, setExamInfo] = useState({
    number: 1,
    answer: "",
    answerNum: "",
  });

  const exampleIcon = ["①", "②", "③", "④"];

  const selectAnswer = (answer) => {
    setExamInfo({ ...examInfo, answerNum: answer });
  };

  // 다음 문제 가져오는 함수
  const getNextQuestion = (e) => {
    e.preventDefault();
    // 정답 체크여부
    let setAnswer = false;

    // 정답을 체크하지 않았을 때
    if (examInfo.answerNum === "") {
      // 넘어가기를 누르면
      if (
        window.confirm(
          "정답을 작성(선택)하지 않으셨습니다.\n정말로 넘어가시겠습니까?"
        )
      ) {
        setAnswer = true;
      }
    }
    // 정답을 체크했을 때
    else {
      setAnswer = true;
    }

    if (setAnswer === true) {
      setExamInfo(
        produce((draft) => {
          draft.number += 1;
          draft.answer = "";
          draft.answerNum = "";
        })
      );
    }

    //모든 문제를 풀었으면 다른 영역 선택하는 화면으로 이동
    if (examInfo.number === exam.length - 1) {
      navi("/select-exam");
    }
  };

  return (
    <Main>
      <Question>
        {examInfo.number}. {exam[examInfo.number].question}
      </Question>
      {exam[examInfo.number].mainText !== false ? (
        <MainText>{exam[examInfo.number].mainText}</MainText>
      ) : (
        ""
      )}
      {exam[examInfo.number].url !== undefined ? (
        <ImgDiv>
          <Img src={exam[examInfo.number].url} />
        </ImgDiv>
      ) : (
        ""
      )}

      <ExamplesDiv>
        {exam[examInfo.number].example.map((example, index) => (
          <Example key={example} onClick={() => selectAnswer(index + 1)}>
            {index + 1 === examInfo.answerNum ? (
              <img
                src="/img/test/check.png"
                style={{ width: "30px", height: "30px" }}
                alt="check"
              />
            ) : (
              <span
                style={{
                  display: "inline-block",
                  width: "30px",
                  height: "30px",
                }}
              >
                {exampleIcon[index]}
              </span>
            )}
            {example}
          </Example>
        ))}
      </ExamplesDiv>
      <ButtonDiv>
        <NextProblemButton onClick={getNextQuestion}>
          다음 문제
        </NextProblemButton>
      </ButtonDiv>
    </Main>
  );
};

export default GrammarExam;
