import styled from "styled-components";
import { produce } from "immer";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

import "App.css";

import VoiceRecorder from "./VoiceRecorder";
import exams from "db/exam_dummy/speaking.json";

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

const BtnDiv = styled.div`
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

const SpeakingExam = () => {
  const navigate = useNavigate();

  const exam = exams.exam;

  // number : 몇 번째 문제
  // answer : 정답 (주관식이면 서술형 답, 객관식이면 보기를 저장)
  // answerNum : 몇 번째 보기인지
  const [examInfo, setExamInfo] = useState({
    number: 1,
    answer: "",
    answerNum: "",
  });

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

    console.log(exam[examInfo.number]);

    //모든 문제를 풀었으면 다른 영역 선택하는 화면으로 이동
    if (examInfo.number === 3) {
      navigate("/select-exam");
    }
  };

  return (
    <Main>
      <VoiceRecorder />

      <Question>
        {examInfo.number}. {exam[examInfo.number].question}
      </Question>

      <BtnDiv>
        <NextProblemButton onClick={getNextQuestion}>
          다음 문제
        </NextProblemButton>
      </BtnDiv>
    </Main>
  );
};

export default SpeakingExam;
