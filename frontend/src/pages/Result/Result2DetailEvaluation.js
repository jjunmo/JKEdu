import styled from "styled-components";
import { useState } from "react";

import "App.css";

const Main = styled.div`
  width: 1000px;
  max-width: 1000px;

  margin: 15px auto;

  @media screen and (max-width: 768px) {
    width: 90%;
  }
`;

// 결과를 나타내기 위한 div
const Title = styled.div`
  font-size: 25px;
  font-family: SCDream9;
  text-align: left;
  @media screen and (max-width: 768px) {
    width: 100%;
    font-size: 14px;
  }
`;

// CEFR LEVEL, 총점, 백분위 text를 나타내는 Header
const Header = styled.div`
  display: flex;
  box-sizing: border-box;
  width: 1000px;
  max-width: 1000px;
  border-bottom: 2px solid #dbe2ef;
  background-color: #f4dfba;
  margin-top: 15px;
  @media screen and (max-width: 768px) {
    width: 100%;
  }
`;
// CEFR LEVEL의 Header
const CEFR = styled.div`
  box-sizing: border-box;
  width: 700px;
  height: 45px;
  line-height: 45px;
  font-size: 20px;
  text-align: center;
  border-right: 2px solid #dbe2ef;

  @media screen and (max-width: 768px) {
    width: 70%;
    font-size: 14px;
  }
`;
// 총점의 Header
const TotalScore = styled(CEFR)`
  width: 150px;
  @media screen and (max-width: 768px) {
    width: 15%;
  }
`;
// 백분위의 Header
const Percent = styled(CEFR)`
  width: 150px;
  @media screen and (max-width: 768px) {
    width: 15%;
  }
`;

// Writing 결과
const Writing = styled.div`
  box-sizing: border-box;
  display: flex;
  width: 1000px;
  height: 80px;
  border-bottom: 2px solid #dbe2ef;
  @media screen and (max-width: 768px) {
    width: 100%;
    height: 40px;
  }
`;
const Listening = styled(Writing)``;
const Speaking = styled(Writing)``;
const Reading = styled(Writing)``;
const Grammar = styled(Writing)``;

// CEFR LEVEL에 해당하는 내용
const CEFRContent = styled.div`
  box-sizing: border-box;
  width: 700px;
  display: flex;
  border-left: 2px solid #dbe2ef;

  @media screen and (max-width: 768px) {
    width: 70%;
  }
`;

// 영역 이름 ex : listening
const ResultName = styled.div`
  box-sizing: border-box;
  border-right: 2px solid #dbe2ef;
  width: 140px;
  line-height: 80px;
  text-align: center;
  @media screen and (max-width: 768px) {
    height: 40px;
    line-height: 40px;
    width: 30%;
    font-size: 14px;
  }
`;

// 해당 영역 점수
const ResultLevel = styled(ResultName)`
  width: 560px;
  text-align: left;
  @media screen and (max-width: 768px) {
    width: 70%;
  }
`;

const Level = styled.div`
  height: 40px;
  line-height: 40px;
`;
const LevelExplain = styled.div`
  height: 40px;
  line-height: 40px;
  @media screen and (max-width: 768px) {
    display: none;
  }
`;
// 맞춘 문제 / 총 문제
const TotalScoreContent = styled.div`
  box-sizing: border-box;
  width: 150px;
  border-right: 2px solid #dbe2ef;

  font-size: 20px;
  line-height: 80px;
  text-align: center;

  @media screen and (max-width: 768px) {
    line-height: 40px;
    width: 15%;
    font-size: 12px;
  }
`;

// 정답의 백분율
const PercentContent = styled(TotalScoreContent)``;

const Result2DetailEvaluation = (prop) => {
  const [result, setResult] = useState(prop.result);
  return (
    <Main>
      <Title>2. 영역별 평가항목 및 환산 점수(Detailed Evaluation)</Title>
      <Header>
        <CEFR>CEFR LEVEL</CEFR>
        <TotalScore>총점</TotalScore>
        <Percent>백분위</Percent>
      </Header>
      <Speaking>
        <CEFRContent>
          <ResultName>Speaking</ResultName>
          <ResultLevel>
            <Level>LEVEL : {result.Speaking.level}</Level>
            <LevelExplain>레벨에 대한 설명</LevelExplain>
          </ResultLevel>
        </CEFRContent>
        <TotalScoreContent>
          {result.Speaking.answer} / {result.Speaking.problem}
        </TotalScoreContent>
        <PercentContent>
          {Math.round((result.Speaking.answer / result.Speaking.problem) * 100)}
          %
        </PercentContent>
      </Speaking>
      <Reading>
        <CEFRContent>
          <ResultName>Reading</ResultName>
          <ResultLevel>
            <Level>LEVEL : {result.Reading.level}</Level>
            <LevelExplain>레벨에 대한 설명</LevelExplain>
          </ResultLevel>
        </CEFRContent>
        <TotalScoreContent>
          {result.Reading.answer} / {result.Reading.problem}
        </TotalScoreContent>
        <PercentContent>
          {Math.round((result.Reading.answer / result.Reading.problem) * 100)}%
        </PercentContent>
      </Reading>
      <Listening>
        <CEFRContent>
          <ResultName>Listening</ResultName>
          <ResultLevel>
            <Level>LEVEL : {result.Listening.level}</Level>
            <LevelExplain>레벨에 대한 설명</LevelExplain>
          </ResultLevel>
        </CEFRContent>
        <TotalScoreContent>
          {result.Listening.answer} / {result.Listening.problem}
        </TotalScoreContent>
        <PercentContent>
          {Math.round(
            (result.Listening.answer / result.Listening.problem) * 100
          )}
          %
        </PercentContent>
      </Listening>
      <Grammar>
        <CEFRContent>
          <ResultName>Grammar</ResultName>
          <ResultLevel>
            <Level>LEVEL : {result.Grammar.level}</Level>
            <LevelExplain>레벨에 대한 설명</LevelExplain>
          </ResultLevel>
        </CEFRContent>
        <TotalScoreContent>
          {result.Grammar.answer} / {result.Grammar.problem}
        </TotalScoreContent>
        <PercentContent>
          {Math.round((result.Grammar.answer / result.Grammar.problem) * 100)}%
        </PercentContent>
      </Grammar>
      <Writing>
        <CEFRContent>
          <ResultName>Writing</ResultName>
          <ResultLevel>
            <Level>LEVEL : {result.Writing.level}</Level>
            <LevelExplain>레벨에 대한 설명</LevelExplain>
          </ResultLevel>
        </CEFRContent>
        <TotalScoreContent>
          {result.Writing.answer} / {result.Writing.problem}
        </TotalScoreContent>
        <PercentContent>
          {Math.round((result.Writing.answer / result.Writing.problem) * 100)}%
        </PercentContent>
      </Writing>
    </Main>
  );
};

export default Result2DetailEvaluation;
