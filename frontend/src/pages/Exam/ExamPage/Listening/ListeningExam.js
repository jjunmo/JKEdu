import styled from "styled-components";
import { produce } from "immer";
import { useNavigate } from "react-router-dom";
import { useState, useRef } from "react";

import "App.css";

import exams from "db/exam_dummy/listening.json";

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

const BtnDiv = styled.div`
  box-sizing: border-box;
  margin-top: 25px;
  text-align: center;
`;

const NextProblemBtn = styled.button`
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

const PlayList = styled.div`
  margin-top: 25px;
`;

const PlayImg = styled.img.attrs(() => ({
  src: "/img/test/headphone.png",
}))`
  width: 50px;
  height: 50px;
  cursor: pointer;
  margin-right: 20px;
`;
const PauseImg = styled(PlayImg).attrs(() => ({
  src: "/img/test/pause.png",
}))``;

const ReadingExam = () => {
  const navi = useNavigate();

  const audioRef = useRef();

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
      pauseAudio();
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
      navi("/select-exam");
    }
  };

  // 오디오 재생
  const playAudio = () => {
    audioRef.current.play();
  };
  // 오디오 다시 듣기
  const resetAudio = () => {
    audioRef.currentTime = 0;
    audioRef.current.load();
    audioRef.current.play();
  };

  // 다음 문제 풀 때 기존의 audio 멈추게 하는 것
  const pauseAudio = () => {
    audioRef.current.pause();
    audioRef.currentTime = 0;
    audioRef.current.load();
  };

  return (
    <Main>
      <PlayList>
        <PlayImg onClick={playAudio}></PlayImg>
        <PauseImg onClick={resetAudio}></PauseImg>
      </PlayList>
      <Question>
        {examInfo.number}. {exam[examInfo.number].question}
      </Question>
      <audio ref={audioRef}>
        <source src={exam[examInfo.number].url} type="audio/mp3" />
      </audio>
      {console.log(audioRef)}
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
      <BtnDiv>
        <NextProblemBtn onClick={getNextQuestion}>다음 문제</NextProblemBtn>
      </BtnDiv>
    </Main>
  );
};

export default ReadingExam;
